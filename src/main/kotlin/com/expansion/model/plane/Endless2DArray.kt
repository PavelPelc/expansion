package com.expansion.model.plane

import java.util.stream.Stream

class Endless2DArray<T> {
    val rows = mutableMapOf<Int,MutableMap<Int,T>>()

    fun exists(x:Int, y:Int): Boolean = get(x,y) != null

    fun exists(index: Dot) = exists(index.x, index.y)
    operator fun get(x:Int, y:Int): T? = rows[y]?.get(x)
    operator fun get(index: Dot): T? = get(index.x, index.y)

    operator fun set(x:Int, y:Int, tile:T) {
        if (rows.containsKey(y)) {
            rows[y]!![x] = tile
        } else {
            rows[y] = mutableMapOf(x to tile)
        }
    }

    operator fun set(index: Dot, tile:T) = set(index.x, index.y, tile)

    fun drop(x:Int, y:Int) {
        if (rows.containsKey(y)) {
            rows[y]!!.remove(x)
            if (rows[y]!!.isEmpty()) {
                rows.remove(y)
            }
        }
    }

    fun isEmpty() = rows.isEmpty()

    fun stream() : Stream<T> = rows.values.stream().flatMap { it.values.stream() }
    fun clear() {
        rows.clear()
    }
}