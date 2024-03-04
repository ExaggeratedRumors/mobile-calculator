package com.ertools.mobile_calculator.model
sealed interface Operation
class OneArgumentOperation(
    val function: (Double) -> (Double),
    val operationType: OneArgumentOperationType
) : Operation
class TwoArgumentOperation(
    val function: (Double, Double) -> (Double),
    val operationType: TwoArgumentOperationType
) : Operation

enum class TwoArgumentOperationType {
    ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION, POWER, ROOT, LOG
}

enum class OneArgumentOperationType {
    SIN, COS, TAN, LN, FACTORIAL, ABS, SQRT, PERCENTAGE, RECIPROCAL
}