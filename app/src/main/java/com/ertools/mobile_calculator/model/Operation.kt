package com.ertools.mobile_calculator.model

import java.io.Serializable

sealed interface Operation : Serializable
class OneArgumentOperation(
    val operationFunction: (Double) -> (Double),
    val operationType: OneArgumentOperationType,
    val requirements: (Double) -> (Boolean) = { true }
) : Operation {
    fun execution(argument: Double): Double {
        if(!requirements(argument)) throw OperationException()
        return operationFunction(argument)
    }
}
class TwoArgumentOperation(
    val operationFunction: (Double, Double) -> (Double),
    val operationType: TwoArgumentOperationType,
    val requirements: (Double, Double) -> (Boolean) = { _, _ -> true }
) : Operation {
    fun execution(firstArgument: Double, secondArgument: Double): Double {
        if(!requirements(firstArgument, secondArgument)) throw OperationException()
        return operationFunction(firstArgument, secondArgument)
    }
}

enum class TwoArgumentOperationType {
    ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION, POWER, LOG, RESULT
}

enum class OneArgumentOperationType {
    SIN, COS, TAN, LN, FACTORIAL, ABS, SQRT, PERCENTAGE, RECIPROCAL, DOUBLE
}

class OperationException : Exception() {
    override val message: String = "Invalid operation arguments."
}