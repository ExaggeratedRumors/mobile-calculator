package com.ertools.mobile_calculator.model

import android.content.Context
import android.widget.TextView
import com.ertools.mobile_calculator.utils.OUT_OF_RANGE
import com.ertools.mobile_calculator.utils.SIMPLE_DIGITS_COUNT
import com.ertools.mobile_calculator.utils.*
import java.io.Serializable
import java.lang.Integer.min
import java.lang.StringBuilder
import java.util.Locale
import kotlin.math.*

class OperationBuilder : Serializable {
    private var firstArgument = StringBuilder()
    private var secondArgument = StringBuilder()
    private var result = StringBuilder()
    private var operation : Operation? = null
    private var significantDigits: Int = SIMPLE_DIGITS_COUNT
    private lateinit var context: Context
    private lateinit var view: TextView
    private lateinit var exceptionHandler: ExceptionHandler

    fun build(
        context: Context,
        view: TextView,
        significantDigits: Int = SIMPLE_DIGITS_COUNT
    ): OperationBuilder {
        this.context = context
        this.view = view
        this.significantDigits = significantDigits
        this.exceptionHandler = ExceptionHandler(context)
        invalidateResult()
        return this
    }

    /**
     * Label section.
     */
    private fun invalidateResult() {
        if(result.isNotEmpty()) view.text = result.toString()
        else if(getArgument().isNotEmpty()) view.text = getArgument().toString()
        else setDefaultLabel()
    }

    private fun setDefaultLabel() {
        view.text = "0"
    }

    private fun saveResult(value: Double) {
        var integerDigits = value.toInt().length()
        val outputString: String
        if(integerDigits > significantDigits) outputString = OUT_OF_RANGE
        else {
            integerDigits = min(integerDigits, significantDigits)
            val decimalDigits = significantDigits - integerDigits
            val format = "%${integerDigits}.${decimalDigits}f"
            outputString = format.format(Locale.ENGLISH, value).trimEnd('0', '.')
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
            OneArgumentOperationType.LN -> OneArgumentOperation(::ln, operation)
            OneArgumentOperationType.FACTORIAL -> OneArgumentOperation(::factorial, operation) { x -> x < 50.0 }
            OneArgumentOperationType.PERCENTAGE -> OneArgumentOperation({x -> 0.01 * x}, operation)
            OneArgumentOperationType.ABS -> OneArgumentOperation({ x -> abs(x) }, operation)
            OneArgumentOperationType.SQRT -> OneArgumentOperation({ x -> x.pow(0.5) }, operation) { x -> x >= .0 }
            OneArgumentOperationType.RECIPROCAL -> OneArgumentOperation({ x -> 1 / x }, operation) { x -> x != .0 }
            OneArgumentOperationType.DOUBLE -> OneArgumentOperation({ x -> x * x }, operation)
        }

        /**
         * Call operation after getting result.
         */
        if(firstArgument.isEmpty() && result.isNotEmpty()) {
            firstArgument.append(result)
            result.clear()
            setDefaultLabel()
        } else return

        val value = try {
            (this.operation as OneArgumentOperation).execution(
                firstArgument.toString().toDouble()
            )
        } catch (e: OperationException) {
            exceptionHandler.handleException(operation)
            .0
        }
        saveResult(value)
    }

    fun executeOperation(operation: TwoArgumentOperationType) {
        this.operation = when(operation) {
            TwoArgumentOperationType.ADDITION -> TwoArgumentOperation({x, y -> x + y}, operation)
            TwoArgumentOperationType.SUBTRACTION -> TwoArgumentOperation({x, y -> x - y}, operation)
            TwoArgumentOperationType.MULTIPLICATION -> TwoArgumentOperation({x, y -> x * y}, operation)
            TwoArgumentOperationType.POWER -> TwoArgumentOperation({x, y -> x.pow(y)}, operation) { x, y -> !(x < .0 && y < 1.0) }
            TwoArgumentOperationType.LOG -> TwoArgumentOperation(::log, operation) { x, y -> !(x == .0 || y == .0 || y == 1.0 || y < .0) }
            TwoArgumentOperationType.DIVISION -> TwoArgumentOperation({x, y -> x / y} , operation) { _, y -> y != .0}
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

        val value = try {
            (this.operation as TwoArgumentOperation).execution(
                firstArgument.toString().toDouble(),
                secondArgument.toString().toDouble()
            )
        } catch (e: OperationException) {
            exceptionHandler.handleException(operation)
            .0
        }
        saveResult(value)
    }

    fun appendDigit(digit: Char) {
        result.clear()
        if(getArgument().isEmpty()) setDefaultLabel()
        if(getArgument().length >= SIMPLE_DIGITS_COUNT) return
        when(digit) {
            '0' -> if(getArgument().length == 1 && getArgument()[0] == '0') return
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
}