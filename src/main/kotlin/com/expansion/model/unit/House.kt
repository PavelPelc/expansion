package com.expansion.model.unit

import com.expansion.model.plane.Dot

data class House (
    val place: Dot,
    var capacity: Int,
    var stored: Int,

    )