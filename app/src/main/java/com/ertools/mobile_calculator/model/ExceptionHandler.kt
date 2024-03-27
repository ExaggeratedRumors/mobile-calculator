package com.ertools.mobile_calculator.model

import android.content.Context
import android.widget.Toast

class ExceptionHandler(private val context: Context) {
    fun handleException(operationType: OneArgumentOperationType) {
        val message = when(operationType) {
            OneArgumentOperationType.SIN -> "Invalid argument for sin function."
            OneArgumentOperationType.COS -> "Invalid argument for cos function."
            OneArgumentOperationType.TAN -> "Invalid argument for tan function."
            OneArgumentOperationType.LN -> "Invalid argument for ln function."
            OneArgumentOperationType.FACTORIAL -> "Whoah, don't be so greedy!"
            OneArgumentOperationType.ABS -> "Invalid argument for abs function."
            OneArgumentOperationType.SQRT -> "Invalid argument for sqrt function."
            OneArgumentOperationType.PERCENTAGE -> "Invalid argument for percentage function."
            OneArgumentOperationType.RECIPROCAL -> "Invalid argument for reciprocal function."
            OneArgumentOperationType.DOUBLE -> "Invalid argument for double function."
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun handleException(operationType: TwoArgumentOperationType) {
        val message = when(operationType) {
            TwoArgumentOperationType.ADDITION -> "Invalid arguments for addition operation."
            TwoArgumentOperationType.SUBTRACTION -> "Invalid arguments for subtraction operation."
            TwoArgumentOperationType.MULTIPLICATION -> "Invalid arguments for multiplication operation."
            TwoArgumentOperationType.DIVISION -> "Second argument of division cannot be 0."
            TwoArgumentOperationType.POWER -> "Incorrect arguments of power."
            TwoArgumentOperationType.LOG -> "Incorrect arguments of log."
            TwoArgumentOperationType.RESULT -> "Invalid arguments for result operation."
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}