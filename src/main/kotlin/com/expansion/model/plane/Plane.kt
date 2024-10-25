package com.expansion.model.plane

import java.util.Objects
import kotlin.random.Random

class Plane() {

    val tiles = Endless2DArray<Tile>()

    init {
        insertRootTile()
    }

    private fun insertRootTile() {
        val r = planeRND.nextInt(128)
        val g = planeRND.nextInt(128)
        val b = planeRND.nextInt(128)

        tiles[0,0] = Tile(0, 0, r, g, b, road())
    }

    private fun generateBorder(x:Int, y:Int, direction: Direction) {
        val center = tiles[x,y]
        val other = tiles[x+direction.dx,y+direction.dy]
        if (center == null || other == null) {
            throw IllegalStateException("Cannot set border between $center and $other")
        }
        val connection = TileConnection(road())
        center.connections[direction] = connection
        other.connections[direction.inverse()] = connection
    }


    fun generateNewTile(x: Int,y:Int) {
        if (tiles[x,y] != null) throw IllegalStateException("The tile already exists $x, $y")

        val connected = Direction.asStream().filter{tiles[x+it.dx, y+it.dy] != null}.toList()

        if (connected.isEmpty()) throw IllegalStateException("Cannot generate unconnected tile $x, $y")

        var r = planeRND.nextInt(128)
        var g = planeRND.nextInt(128)
        var b = planeRND.nextInt(128)

        if (planeRND.nextBoolean() && planeRND.nextBoolean()) {
            when (planeRND.nextInt(3)) {
                0 -> r += 128
                1 -> g += 128
                2 -> b += 128
                else -> throw IllegalStateException("Unexpected random value")
            }
        }
        tiles[x,y] = Tile(x,y,r,g,b, road())
        connected.forEach { dir ->
            generateBorder(x,y,dir)
        }

    }

    fun evaluateRoads(x:Int, y:Int) : Endless2DArray<Int> {
        if (tiles[x,y] == null) throw IllegalStateException("Cannot compute roads from void $x, $y")

        //init the algorithm
        val computed = Endless2DArray<Int>()
        computed[x,y] = 0
        floodEvaluateRoads(x,y,computed)
        return computed
    }

    fun route(from:Dot, to:Dot) : List<Dot> {
        if (tiles[from.x,from.y] == null) throw IllegalStateException("Cannot route from void ${from.x}, ${from.y}")

        //init the algorithm
        val computed = Endless2DArray<Int>()
        computed[from.x,from.y] = 0
        val relevant = mutableListOf(Dot(from.x,from.y))

        val routeTree = Endless2DArray<Direction>()
        while (!computed.exists(to)) {

            val border = Endless2DArray<Int>()
            fillBorder(computed, border, relevant, routeTree)
            if (border.isEmpty()) break

            var lowest = Int.MAX_VALUE
            var lowestPos : Dot? = null
            border.rows.forEach {row ->
                row.value.forEach { column ->
                    if (border[column.key, row.key]!! < lowest) {
                        lowest = border[column.key, row.key]!!
                        lowestPos = Dot(column.key, row.key)
                    }
                }
            }
            val x = lowestPos!!.x
            val y = lowestPos!!.y
            computed[x,y]=lowest
            relevant.add(Dot(x,y))
            tiles[x,y]!!.connections.forEach {con ->
                val otherX = x + con.key.dx
                val otherY = y + con.key.dy
                if (!computed.exists(otherX,otherY)) {
                    val len = border[x,y]!! +
                            tiles[x, y]!!.getHalfRoad() +
                            con.value.road +
                            tiles[otherX, otherY]!!.getHalfRoad()
                    if (!border.exists(otherX,otherY) || border[x,y]!! > len) {
                        border[otherX, otherY] = len
                    }
                }
            }

        }
        val route = mutableListOf(to)
        while (!Objects.equals(route[0],from) ) {
            val direction = routeTree[route[0]]!!
            route.add(0,Dot(route[0].x + direction.dx, route[0].y + direction .dy))
        }

        return route
    }

    private fun floodEvaluateRoads(x:Int, y:Int, computed: Endless2DArray<Int> ) {
        var counter = 0L;
        val relevant = mutableListOf(Dot(x,y))
        while (true) {
            counter++

            val border = Endless2DArray<Int>()
            fillBorder(computed, border, relevant)
            if (counter % 1000 == 0L) {
                println("${counter/1000} .. R: ${computed.stream().count()} B: ${border.stream().count()} $border")
            }
            if (border.isEmpty()) break

            var lowest = Int.MAX_VALUE
            var lowestPos : Dot? = null
            border.rows.forEach {row ->
                row.value.forEach { column ->
                    if (border[column.key, row.key]!! < lowest) {
                        lowest = border[column.key, row.key]!!
                        lowestPos = Dot(column.key, row.key)
                    }
                }
            }
            val x = lowestPos!!.x
            val y = lowestPos!!.y
            computed[x,y] = lowest
            relevant.add(Dot(x,y))
            //print("[$x,$y]")
            //counter++
            //if (counter > 10) {
                //println()
               // counter = 0
            //}
            tiles[x,y]!!.connections.forEach {con ->
                val otherX = x + con.key.dx
                val otherY = y + con.key.dy
                if (!computed.exists(otherX,otherY)) {
                    val len = border[x,y]!! +
                            tiles[x, y]!!.getHalfRoad() +
                            con.value.road +
                            tiles[otherX, otherY]!!.getHalfRoad()
                    if (!border.exists(otherX,otherY) || border[x,y]!! > len) {
                        border[otherX, otherY] = len
                    }
                }
            }

        }
        //println()
    }


    private fun fillBorder(
        computed: Endless2DArray<Int>,
        border: Endless2DArray<Int>,
        relevant: MutableList<Dot>,
        tree: Endless2DArray<Direction>? = null
    ) {
        val irrelevant = mutableListOf<Dot>()
        relevant.forEach{dot ->
                val x = dot.x
                val y = dot.y
                    var helpful = false
                    tiles[x, y]!!.connections.forEach { con ->
                        val otherX = x + con.key.dx
                        val otherY = y + con.key.dy
                        if (!computed.exists(otherX, otherY)) {
                            helpful = true
                            val len = computed[x,y]!! +
                                    tiles[x, y]!!.getHalfRoad() +
                                    con.value.road +
                                    tiles[otherX, otherY]!!.getHalfRoad()
                            if (!border.exists(otherX, otherY) || border[otherX, otherY]!! > len) {
                                border[otherX, otherY] = len
                                tree?.let { it[otherX, otherY] = con.key.inverse() }
                            }
                        }
                    }
                    if (!helpful) {
                        irrelevant.add(dot)
                    }

        }
        relevant.removeAll(irrelevant)
    }

    companion object {
        val planeRND = Random(System.currentTimeMillis())

        fun road() = 500 + planeRND.nextInt(1000)
    }

}