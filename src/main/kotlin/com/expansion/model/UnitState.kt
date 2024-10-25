package com.expansion.model

import com.expansion.model.plane.Dot

data class UnitState (
    val interruptQueue: MutableList<StateInterrupts> = mutableListOf(),
    val goalQueue:  MutableList<StateGoals> = mutableListOf(),
    val subGoalQueue:  MutableList<StateSubGoals> = mutableListOf(),
    val headingTo : Dot? = null,
    val subHeadingTo : Dot? = null,
    val position : Dot? = null,
    val subProspected : Int = 0,


){
    init {
        interruptQueue.add(StateInterrupts.BORN)
    }
}

enum class StateInterrupts {
    RECOMPUTE_PLAN,

    BORN,
    ATTACK,
    DIE
}

enum class StateGoals {
    PROSPECTING,
    HOUSE_BUILD,
    ROAD_UPGRADE,
    MINE_BUILD,
    DUPLICATE,
    SELF_UPGRADE,
}

enum class StateSubGoals {
    MOVE,
    UPGRADE,
    PROSPECT,
    BUILD,
}
