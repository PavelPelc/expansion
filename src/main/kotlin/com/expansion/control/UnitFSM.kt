package com.expansion.control

import com.expansion.model.unit.HUnit

class UnitFSM {

    fun processUnit(time: Long, unit: HUnit) {
        checkInterrupts(unit)
        if (unit.state.subGoalQueue.isEmpty()) {
            if (unit.state.goalQueue.isEmpty()) {

            }
        }
    }

    private fun checkInterrupts(unit: HUnit) {
        if (!unit.state.interruptQueue.isEmpty()) {
            val interrupt = unit.state.interruptQueue[0]
            when (unit.state.interruptQueue[0]) {
                else -> {}
            }
        }
    }
}