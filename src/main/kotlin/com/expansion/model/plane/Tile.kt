package com.expansion.model.plane

data class Tile (
    val x:Int,
    val y:Int,
    val red: Int,
    val green: Int,
    val blue: Int,
    var road: Int,

    var knownTo: String = "",
    var roadTicks: Int = 0,
    var roadUpgrades: Int = 0,
    ) {
    val connections = mutableMapOf<Direction,TileConnection>()
    fun listConnected() : List<Dot> = connections.entries.stream()
        .map {Dot(x+it.key.dx, y+it.key.dy)}
        .toList()

    fun getHalfRoad() = road / 2 + road % 2

    fun knownBy(hiveId:String) = knownTo.contains(hiveId)

    fun exploredBy(hiveId:String)  { if (!knownTo.contains(hiveId)) knownTo = knownTo + hiveId }
}

enum class Direction(val dx:Int, val dy: Int) {
    NORTH(0,-1),
    EAST(1,0),
    SOUTH(0,1),
    WEST(-1,0),
    ;

    fun inverse() = when(this) {
        NORTH -> SOUTH
        EAST -> WEST
        SOUTH -> NORTH
        WEST -> EAST
    }
    companion object {
        fun asStream() = listOf(NORTH, EAST, SOUTH, WEST).stream()
    }
}

data class TileConnection(
    var road : Int,
)