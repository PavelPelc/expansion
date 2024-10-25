package com.expansion.control

import com.expansion.model.HUnit
import com.expansion.model.StateGoals
import com.expansion.model.StateInterrupts

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
                StateInterrupts.BORN -> {
                    unit.state.goalQueue.add(0, StateGoals.PROSPECTING)
                    unit.state.goalQueue.add(1, StateGoals.HOUSE_BUILD)
                    unit.state.interruptQueue[0]
                    unit.state.subGoalQueue.clear()
                }
                else -> {}
            }
        }
    }
}