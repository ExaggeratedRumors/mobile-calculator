package com.ertools.mobile_calculator.model

import android.content.Context
import android.widget.TextView
import com.ertools.mobile_calculator.utils.OUT_OF_RANGE
import com.ertools.mobile_calculator.utils.SIMPLE_DIGITS_COUNT
import com.ertools.mobile_calculator.utils.*
import java.io.Serializable
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
    private var isInitialized = false

    /**
     * Build object with context and view.
     * This is necessary operation.
     */
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
        isInitialized = true
        return this
    }

    /**
     * Set label to default value.
     */
    private fun setDefaultLabel() {
        view.text = "0"
    }

    /**
     * Get current input
     */
    private fun getArgument(): StringBuilder {
        return if(operation == null) firstArgument else secondArgument
    }

    /**
     * Set view label as current input
     */
    private fun invalidateResult() {
        if(secondArgument.isNotEmpty()) view.text = secondArgument.toString()
        else if(firstArgument.isNotEmpty()) view.text = firstArgument.toString()
        else if(result.isNotEmpty()) view.text = result.toString()
        else setDefaultLabel()
    }

    /**
     * Change result before append next digit
     */
    private fun prepareResult() {
        if(result.isNotEmpty()) {
            if(firstArgument.isEmpty() && operation != null) resultToFirstArgument()
            else result.clear()
        }
        if(getArgument().isEmpty()) setDefaultLabel()
    }

    /**
     * Move result to first argument.
     */
    private fun resultToFirstArgument(){
        firstArgument.clear()
        firstArgument.append(result)
        result.clear()
    }

    /**
     * Calculate and save result of the operation.
     */
    private fun saveResult(value: Double) {
        var integerDigits = value.toLong().toString().length
        if(value < .0) integerDigits += 1
        val outputString: String = if(integerDigits > significantDigits) OUT_OF_RANGE
        else {
            val decimalDigits = significantDigits - integerDigits - 1
            val format = "%${integerDigits}.${decimalDigits}f"
            format
                .format(Locale.ENGLISH, value)
                .trimEnd('0')
                .trimEnd('.')
        }
        clearData()
        result.append(outputString)
    }

    private fun isOperationAbandoned(): Boolean {
        if(firstArgument.isEmpty() && result.isEmpty()) {
            operation = null
            return true
        }
        return false
    }

    /** ===================================
     * ============== API =================
     * =================================== */

    /**
     * Clear current input.
     */
    fun clearInput() {
        if(!isInitialized) return
        if(getArgument().isNotEmpty()) getArgument().clear()
        else if(firstArgument.isNotEmpty() && operation != null) firstArgument.clear()
        else clearData()
        setDefaultLabel()
    }

    /**
     * Clear both arguments, result and operation.
     */
    fun clearData() {
        if(!isInitialized) return
        firstArgument.clear()
        secondArgument.clear()
        result.clear()
        operation = null
        setDefaultLabel()
    }

    /**
     * Execute one-operand operation.
     */
    fun executeOperation(operation: OneArgumentOperationType) {
        if(!isInitialized) return
        if(BUILDER_DEBUG)
            println("DEBUG - before one execute; First: $firstArgument, Second: $secondArgument, Result: $result, Op: $operation")
        this.operation = when(operation) {
            OneArgumentOperationType.SIN -> OneArgumentOperation(::sin, operation)
            OneArgumentOperationType.COS -> OneArgumentOperation(::cos, operation)
            OneArgumentOperationType.TAN -> OneArgumentOperation(::tan, operation)
            OneArgumentOperationType.LN -> OneArgumentOperation(::ln, operation)
            OneArgumentOperationType.FACTORIAL -> OneArgumentOperation(::factorial, operation) { x -> x < 20.0 }
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
            resultToFirstArgument()
        } else if(firstArgument.isEmpty()) return

        val value = try {
            (this.operation as OneArgumentOperation).execution(
                firstArgument.toString().toDouble()
            )
        } catch (e: OperationException) {
            exceptionHandler.handleException(operation)
            clearData()
            .0
        }
        saveResult(value)
        invalidateResult()

        if(BUILDER_DEBUG)
            println("DEBUG - after one execute; First: $firstArgument, Second: $secondArgument, Result: $result, Op: $operation")
    }

    /**
     * Execute two-operand operation.
     * Service operation executions without calling the result (equals-button).
     */
    fun executeOperation(operation: TwoArgumentOperationType) {
        if(!isInitialized) return

        if(BUILDER_DEBUG)
            println("DEBUG - before two execute; First: $firstArgument, Second: $secondArgument, Result: $result, Op: $operation")
        if(isOperationAbandoned()) return

        val nextOperation = when(operation) {
            TwoArgumentOperationType.ADDITION -> TwoArgumentOperation({x, y -> x + y}, operation)
            TwoArgumentOperationType.SUBTRACTION -> TwoArgumentOperation({x, y -> x - y}, operation)
            TwoArgumentOperationType.MULTIPLICATION -> TwoArgumentOperation({x, y -> x * y}, operation)
            TwoArgumentOperationType.POWER -> TwoArgumentOperation({x, y -> x.pow(y)}, operation) { x, y -> !(x < .0 && y < 1.0) }
            TwoArgumentOperationType.LOG -> TwoArgumentOperation(::log, operation) { x, y -> !(x == .0 || y == .0 || y == 1.0 || y < .0) }
            TwoArgumentOperationType.DIVISION -> TwoArgumentOperation({x, y -> x / y} , operation) { _, y -> y != .0}
            TwoArgumentOperationType.RESULT -> null
        }
        if(this.operation == null) this.operation = nextOperation
        if(this.operation == null) return

        /**
         * Call operation after getting result.
         */
        if(result.isNotEmpty() && firstArgument.isEmpty()) {
            resultToFirstArgument()
            this.operation = nextOperation
            invalidateResult()
            return
        } else if(result.isNotEmpty() && firstArgument.isNotEmpty() && secondArgument.isEmpty()) {
            firstArgument.clear()
            firstArgument.append(result.toString())
            secondArgument.append(firstArgument.toString())
            result.clear()
        } else if(firstArgument.isEmpty() || secondArgument.isEmpty()) return

        val value = try {
            (this.operation as TwoArgumentOperation).execution(
                firstArgument.toString().toDouble(),
                secondArgument.toString().toDouble()
            )
        } catch (e: OperationException) {
            exceptionHandler.handleException((this.operation as TwoArgumentOperation).operationType)
            clearData()
            .0
        }
        saveResult(value)
        invalidateResult()
        this.operation = nextOperation

        if(BUILDER_DEBUG)
            println("DEBUG - after two execute; First: $firstArgument, Second: $secondArgument, Result: $result, Op: $operation")
    }

    /**
     * Add digit, point or append/remove minus sign.
     */
    fun appendDigit(digit: Char) {
        if(!isInitialized) return
        if(BUILDER_DEBUG)
            println("DEBUG - before append; First: $firstArgument, Second: $secondArgument, Result: $result, Op: $operation")

        prepareResult()
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

        if(BUILDER_DEBUG)
            println("DEBUG - after append; First: $firstArgument, Second: $secondArgument, Result: $result, Op: $operation")
    }
}