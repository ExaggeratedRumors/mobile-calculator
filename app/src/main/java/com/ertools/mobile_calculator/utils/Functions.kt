package com.ertools.mobile_calculator.utils

import kotlin.math.abs
import kotlin.math.log10

fun Int.length() = when(this) {
    0 -> 1
    else -> log10(abs(toDouble())).toInt() + 1
}