package com.expansion.model

import com.expansion.model.plane.Dot

data class HUnit (
    val id: Int,
    val hiveId: String,
    val state: UnitState,

    var x: Int,
    var y: Int,
    var house: Dot? = null,

    var redCarry: Int = 0,
    var greenCarry: Int = 0,
    var blueCary: Int = 0,
    var whiteCary: Int = 0,

    var redCapacity: Int = 1,
    var greenCapacity: Int = 1,
    var blueCapacity: Int = 1,
    var whiteCapacity: Int = 1,
    var damagePower: Int = 1,
    var movePower: Int = 1,

    var redCapacityDamage: Int? = null,
    var greenCapacityDamage: Int? = null,
    var blueCapacityDamage: Int? = null,
    var whiteCapacityDamage: Int? = null,
    var damagePowerDamage: Int? = null,
    var movePowerDamage: Int? = null,

    var prospected : Int = 0,
    var tacho : Int = 0,
    var timeOnRoad: Int = 0, //ms
    var damages : Int = 0,
    var attacks : Int = 0,
    var works : Int = 0,
    var childs : Int = 0,

    ) {
     fun size() = redCapacity + greenCapacity + redCapacity + whiteCapacity + damagePower + movePower

     fun speed() = movePower * 6000 / size() // travel units per second

     fun name() = hiveId + id
}