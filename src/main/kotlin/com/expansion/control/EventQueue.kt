package com.expansion.control

import com.expansion.model.Event
import java.util.Comparator

class EventQueue : ArrayList<Event>() {
    override fun add(element: Event): Boolean {
        var index = 0;
            for (i in 0 until size) {
                index = size;
                if (this[i].time > element.time) {
                    index = i
                    break
                }
            }
        super.add(index,element)
        return true
    }

    override fun add(index: Int, element: Event) {
        throw IllegalAccessException("Forbidden.")
    }

    override fun addAll(elements: Collection<Event>): Boolean {
        throw IllegalAccessException("Forbidden.")
    }

    override fun addAll(index: Int, elements: Collection<Event>): Boolean {
        throw IllegalAccessException("Forbidden.")
    }

    override fun set(index: Int, element: Event): Event {
        throw IllegalAccessException("Forbidden.")
    }

    override fun sort(c: Comparator<in Event>?) {
        throw IllegalAccessException("Forbidden.")
    }
}