package com.ertools.mobile_calculator.model

import android.content.Context
import android.widget.TextView
import android.widget.Toast
import com.ertools.mobile_calculator.utils.OUT_OF_RANGE
import com.ertools.mobile_calculator.utils.SIMPLE_DIGITS_COUNT
import com.ertools.mobile_calculator.utils.*
import java.io.Serializable
import java.lang.Integer.min
import java.lang.StringBuilder
import java.util.Locale
import kotlin.math.*

class OperationBuilder(
    private val context: Context,
    private val view: TextView,
    private val significantDigits: Int = SIMPLE_DIGITS_COUNT
): Serializable {
    private var firstArgument = StringBuilder()
    private var secondArgument = StringBuilder()
    private var result = StringBuilder()
    private var operation : Operation? = null

    /**
     * Label section.
     */
    private fun setDefaultLabel() {
        view.text = "0"
    }

    private fun invalidateResult() {
        if(result.isEmpty()) view.text = getArgument().toString()
        else view.text = result.toString()
    }

    private fun saveResult(value: Double) {
        var integerDigits = value.toInt().length()
        val outputString: String
        if(integerDigits > significantDigits) outputString = OUT_OF_RANGE
        else {
            integerDigits = min(integerDigits, significantDigits)
            val decimalDigits = significantDigits - integerDigits
            val format = "%${integerDigits}.${decimalDigits}f"
            outputString = format.format(Locale.ENGLISH, value)
        }
        clearData()
        result.append(outputString)
        invalidateResult()
    }

    /**
     * Builder section.
     */
    private fun getArgument(): StringBuilder {
        return if(operation == null) firstArgument else secondArgument
    }

    fun clearInput() {
        if(secondArgument.isNotEmpty()) secondArgument.clear()
        else if(firstArgument.isNotEmpty() && operation != null) firstArgument.clear()
        else clearData()
        setDefaultLabel()
    }

    fun clearData() {
        firstArgument.clear()
        secondArgument.clear()
        result.clear()
        operation = null
        setDefaultLabel()
    }

    fun executeOperation() {
        when(this.operation) {
            is OneArgumentOperation -> executeOperation(
                (this.operation as OneArgumentOperation).operationType
            )
            is TwoArgumentOperation -> executeOperation(
                (this.operation as TwoArgumentOperation).operationType
            )
            else -> return
        }
    }

    fun executeOperation(operation: OneArgumentOperationType) {
        this.operation = when(operation) {
            OneArgumentOperationType.SIN -> OneArgumentOperation(::sin, operation)
            OneArgumentOperationType.COS -> OneArgumentOperation(::cos, operation)
            OneArgumentOperationType.TAN -> OneArgumentOperation(::tan, operation)
            OneArgumentOperationType.FACTORIAL -> OneArgumentOperation(::factorial, operation)
            OneArgumentOperationType.PERCENTAGE -> OneArgumentOperation({x -> 0.01 * x}, operation)
            OneArgumentOperationType.ABS -> OneArgumentOperation({ x -> abs(x) }, operation)
            OneArgumentOperationType.SQRT -> OneArgumentOperation({ x -> power(x, 0.5) }, operation)
            OneArgumentOperationType.LN -> OneArgumentOperation({ x -> ln(x) }, operation)
            OneArgumentOperationType.RECIPROCAL -> OneArgumentOperation({ x -> 1 / x }, operation)
            OneArgumentOperationType.DOUBLE -> OneArgumentOperation({ x -> x * x }, operation)
        }

        /**
         * Call operation after getting result.
         */
        if(firstArgument.isEmpty()) {
            firstArgument.append(result)
            result.clear()
            setDefaultLabel()
            return
        }

        val value = (this.operation as OneArgumentOperation).function.invoke(
            firstArgument.toString().toDouble()
        )
        saveResult(value)
    }

    fun executeOperation(operation: TwoArgumentOperationType) {
        this.operation = when(operation) {
            TwoArgumentOperationType.ADDITION -> TwoArgumentOperation({x, y -> x + y}, operation)
            TwoArgumentOperationType.SUBTRACTION -> TwoArgumentOperation({x, y -> x - y}, operation)
            TwoArgumentOperationType.MULTIPLICATION -> TwoArgumentOperation({x, y -> x * y}, operation)
            TwoArgumentOperationType.POWER -> TwoArgumentOperation(::power, operation)
            TwoArgumentOperationType.LOG -> TwoArgumentOperation(::logarithm, operation)
            TwoArgumentOperationType.DIVISION -> TwoArgumentOperation(::division, operation)
        }

        /**
         * Call operation after getting result.
         */
        if(firstArgument.isEmpty() && secondArgument.isEmpty()) {
            firstArgument.append(result)
            result.clear()
            setDefaultLabel()
            return
        }

        /**
         * Incorrect call
         */
        if(firstArgument.isEmpty() || secondArgument.isEmpty()) {
            setDefaultLabel()
            return
        }

        val value = (this.operation as TwoArgumentOperation).function.invoke(
            firstArgument.toString().toDouble(),
            secondArgument.toString().toDouble()
        )
        saveResult(value)
    }

    fun appendDigit(digit: Char) {
        result.clear()
        if(getArgument().isEmpty()) setDefaultLabel()
        if(getArgument().length >= SIMPLE_DIGITS_COUNT) return
        when(digit) {
            '0' -> if(getArgument().isEmpty()) return
            '.' -> {
                if(getArgument().contains('.')) return
                if(getArgument().length >= SIMPLE_DIGITS_COUNT - 1) return
                if(getArgument().isEmpty()) getArgument().append('0')
            }
            '-' -> {
                if(getArgument().length == 1 && getArgument()[0] == '0') return
                if(getArgument().contains('-')) getArgument().deleteCharAt(0)
                else getArgument().insert(0, '-')
                invalidateResult()
                return
            }
            else -> {
                if(getArgument().length == 1 && getArgument()[0] == '0') getArgument().clear()
            }
        }
        getArgument().append(digit)
        invalidateResult()
    }


    /**
     * Special operations
     */

    private fun factorial(x: Double): Double {
        return if (x > 50) {
            Toast.makeText(
                context,
                "Whoah don't be so grady!",
                Toast.LENGTH_SHORT
            ).show()
            clearData()
            .0
        } else when (x) {
            0.0, 1.0 -> 1.0
            else -> (1..x.toInt()).map { it.toDouble() }.reduce { a, b -> a * b }
        }
    }

    private fun division(x: Double, y: Double): Double {
        if(y == .0) {
            Toast.makeText(
                context,
                "Second argument of division cannot be 0.",
                Toast.LENGTH_SHORT
            ).show()
            clearData()
            return .0
        }
        return x / y
    }

    private fun logarithm(x: Double, y: Double): Double {
        if(x == .0 || y == .0 || y == 1.0 || y < 0) {
            Toast.makeText(
                context,
                "Incorrect arguments of log.",
                Toast.LENGTH_SHORT
            ).show()
            clearData()
            return .0
        }
        return log(x, y)
    }

    private fun power(x: Double, y: Double): Double {
        if(x < 0 && y < 1) {
            Toast.makeText(
                context,
                "Incorrect arguments of power.",
                Toast.LENGTH_SHORT
            ).show()
            clearData()
            return .0
        }
        return x.pow(y)
    }
}