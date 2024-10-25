package com.expansion.model

import com.expansion.model.plane.Direction
import com.expansion.model.plane.Dot
import com.expansion.model.plane.Endless2DArray
import com.expansion.model.plane.Plane
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows


class TestPlane {

    @Test
    fun testGenerate() {
        val animal = Plane()
        for (i in 0 .. 99) {
            for (j in 0 .. 999 ) {
                if (i == 0 && j == 0) continue
                animal.generateNewTile(i,j)
            }
        }


        assertEquals(100000,animal.tiles.stream().count(), "Count does not match")
        assertThrows<IllegalStateException> { animal.generateNewTile(0,0) }
        assertThrows<IllegalStateException> { animal.generateNewTile(99,99) }
        assertDoesNotThrow{ animal.generateNewTile(0,-1) }


    }

    @Test
    fun testEvaluateRoadHugeCompute() {

        val animal = Plane()
        for (i in 0 .. 99) {
            for (j in 0 .. 999 ) {
                if (i == 0 && j == 0) continue
                animal.generateNewTile(i,j)
            }
        }
        println("generated")

        var subResult = animal.evaluateRoads(0,0)

        assertEquals(100000, subResult.stream().count())

    }

    @Test
    fun testEvaluateRoadSmallDisplay() {

        val animal = Plane()
        for (i in 0 .. 10) {
            for (j in 0 .. 20 ) {
                if (i == 0 && j == 0) continue
                animal.generateNewTile(i,j)
            }
        }

        var subResult = animal.evaluateRoads(0,0)
        dump(subResult)
        dump2(subResult, animal)
    }

    @Test
    fun testEvaluateRoadSmallDisplay2() {

        val animal = Plane()
        for (j in 0 .. 20) {
            for (i in 0 .. 10 ) {
               // println("$i x $j")
                if (i != 0 || j != 0) animal.generateNewTile(i,j)
               // println(animal.tiles[i,j])
                if (j % 2 == 0) {
                    animal.tiles[i,j]!!.road = 1
                    animal.tiles[i,j]!!.connections[Direction.WEST]?.let { it.road = 1 }
                    if (j % 4 == 0 && i == 0) {
                        animal.tiles[i,j]!!.connections[Direction.NORTH]?.let { it.road = 1 }
                    }
                    if (j % 4 == 2 && i == 10) {
                        animal.tiles[i,j]!!.connections[Direction.NORTH]?.let { it.road = 1 }
                    }
                } else {
                    if (j % 4 == 1 && i == 10) {
                        animal.tiles[i,j]!!.road = 1
                        animal.tiles[i,j]!!.connections[Direction.NORTH]?.let { it.road = 1 }
                    }
                    if (j % 4 == 3 && i == 0) {
                        animal.tiles[i,j]!!.road = 1
                        animal.tiles[i,j]!!.connections[Direction.NORTH]?.let { it.road = 1 }
                    }
                }
            }
        }



        var subResult = animal.evaluateRoads(0,0)
        dump(subResult)
        dump2(subResult, animal)
        assertEquals(390, subResult.get(10,20))
        subResult = animal.evaluateRoads(5,10)
        assertEquals(195, subResult.get(10,20))
        assertEquals(195, subResult.get(0,0))

    }

    private fun dump2(subResult: Endless2DArray<Int>, animal: Plane) {
        val sb = StringBuilder()
        for (j in 0 .. 20 ) {
            for (i in 0 .. 10) {
                var len = sb.length
                sb.append(subResult[i,j])
                var tab = 7 - sb.length + len
                for (k in 1 .. tab) sb.append(' ')
                len = sb.length
                sb.append(animal.tiles[i,j]!!.road)
                tab = 5 - sb.length + len
                for (k in 1 .. tab) sb.append(' ')
                len = sb.length
                sb.append(animal.tiles[i,j]!!.connections[Direction.EAST]?.road)
                tab = 5 - sb.length + len
                for (k in 1 .. tab) sb.append(' ')
                sb.append("|")
            }
            sb.append("\n")
            for (i in 0 .. 10) {
                val len = sb.length
                sb.append(animal.tiles[i,j]!!.connections[Direction.SOUTH]?.road)
                val tab = 18 - sb.length + len
                for (k in 1 .. tab) sb.append(' ')
            }
            sb.append("\n")
        }
        println(sb.toString())

    }

    private fun dump(animal: Endless2DArray<Int>) {
        val sb = StringBuilder()
        for (j in 0 .. 20 ) {
            for (i in 0 .. 10) {
                val len = sb.length
                sb.append(animal[i,j])
                val tab = 6 - sb.length + len
                for (k in 1 .. tab) sb.append(' ')
            }
            sb.append("\n")
        }
        println(sb.toString())
    }

    @Test
    fun testEvaluateRoute() {

        val animal = Plane()
        for (j in 0 .. 20) {
            for (i in 0 .. 10 ) {
                // println("$i x $j")
                if (i != 0 || j != 0) animal.generateNewTile(i,j)
                // println(animal.tiles[i,j])
                if (j % 2 == 0) {
                    animal.tiles[i,j]!!.road = 1
                    animal.tiles[i,j]!!.connections[Direction.WEST]?.let { it.road = 1 }
                    if (j % 4 == 0 && i == 0) {
                        animal.tiles[i,j]!!.connections[Direction.NORTH]?.let { it.road = 1 }
                    }
                    if (j % 4 == 2 && i == 10) {
                        animal.tiles[i,j]!!.connections[Direction.NORTH]?.let { it.road = 1 }
                    }
                } else {
                    if (j % 4 == 1 && i == 10) {
                        animal.tiles[i,j]!!.road = 1
                        animal.tiles[i,j]!!.connections[Direction.NORTH]?.let { it.road = 1 }
                    }
                    if (j % 4 == 3 && i == 0) {
                        animal.tiles[i,j]!!.road = 1
                        animal.tiles[i,j]!!.connections[Direction.NORTH]?.let { it.road = 1 }
                    }
                }
            }
        }

        var subResult = animal.route(Dot(0,0), Dot(10,20));
        assertEquals(131, subResult.size)
        assertEquals(Dot(0,0), subResult[0])
        assertEquals(Dot(1,0), subResult[1])
        assertEquals(Dot(10,20), subResult[130])
        assertEquals(Dot(9,20), subResult[129])

    }



}