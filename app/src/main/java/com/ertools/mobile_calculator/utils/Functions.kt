package com.ertools.mobile_calculator.utils

import kotlin.math.abs
import kotlin.math.log10

fun Int.length() = when(this) {
    0 -> 1
    else -> log10(abs(toDouble())).toInt() + 1
}

fun factorial(x: Double) = when (x) {
    0.0, 1.0 -> 1.0
    else -> (1..x.toInt()).map { it.toDouble() }.reduce { a, b -> a * b }
}