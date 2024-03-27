package com.ertools.mobile_calculator.utils

import kotlin.math.abs
import kotlin.math.log10

fun factorial(x: Double) = when (x.toInt()) {
    0, 1 -> 1.0
    else -> (1..x.toInt()).map { it.toDouble() }.reduce { a, b -> a * b }
}