package com.ertools.mobile_calculator.computing

import kotlin.math.*

fun add(a: Double, b: Double) = a + b
fun subtract(a: Double, b: Double) = a - b
fun multiply(a: Double, b: Double) = a * b
fun divide(a: Double, b: Double) = a / b
fun power(a: Double, b: Double) = a.pow(b)
fun root(a: Double, b: Double) = a.pow(1.0 / b)
fun sin(x: Double, y: Double) = kotlin.math.sin(x)
fun cos(x: Double, y: Double) = kotlin.math.cos(x)
fun tan(x: Double, y: Double) = kotlin.math.tan(x)
fun arcsin(x: Double, y: Double) = asin(x)
fun arccos(x: Double, y: Double) = acos(x)
fun arctan(x: Double, y: Double) = atan(x)
fun percent(x: Double, y: Double) = x * 0.01
fun negate(x: Double, y: Double) = -x
fun reciprocal(x: Double, y: Double) = 1 / x
fun factorial(x: Double, y: Double) = when (x) {
    0.0, 1.0 -> 1.0
    else -> (1..x.toInt()).map { it.toDouble() }.reduce { a, b -> a * b }
}
