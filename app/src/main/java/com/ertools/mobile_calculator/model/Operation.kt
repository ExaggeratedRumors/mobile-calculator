package com.ertools.mobile_calculator.model

import java.io.Serializable

sealed interface Operation : Serializable
class OneArgumentOperation(
    val function: (Double) -> (Double),
    val operationType: OneArgumentOperationType
) : Operation
class TwoArgumentOperation(
    val function: (Double, Double) -> (Double),
    val operationType: TwoArgumentOperationType
) : Operation

enum class TwoArgumentOperationType {
    ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION, POWER, LOG
}

enum class OneArgumentOperationType {
    SIN, COS, TAN, LN, FACTORIAL, ABS, SQRT, PERCENTAGE, RECIPROCAL, DOUBLE
}