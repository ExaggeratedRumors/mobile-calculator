package com.ertools.mobile_calculator.view

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ertools.mobile_calculator.R
import com.ertools.mobile_calculator.model.OneArgumentOperationType
import com.ertools.mobile_calculator.model.OperationBuilder
import com.ertools.mobile_calculator.model.TwoArgumentOperationType
import com.ertools.mobile_calculator.utils.OPERATION_BUILDER_STATE

class SimpleCalculatorActivity : AppCompatActivity() {
    private lateinit var label: TextView
    private var operationBuilder: OperationBuilder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_calculator)
        label = findViewById(R.id.simple_result)
        label.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_END
        loadOperationBuilderState(savedInstanceState)
        numericButtonsHandle()
        operationButtonsHandle()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(OPERATION_BUILDER_STATE, operationBuilder)
        super.onSaveInstanceState(outState)
    }

    private fun loadOperationBuilderState(savedInstanceState: Bundle?) {
        operationBuilder = savedInstanceState
                ?.getSerializable(OPERATION_BUILDER_STATE) as OperationBuilder?
                ?: OperationBuilder()
        operationBuilder?.build(applicationContext, label)
    }

    private fun operationButtonsHandle() {
        val operationButtons = mapOf(
            R.id.simple_clear to { operationBuilder?.clearInput() },
            R.id.simple_clear_all to { operationBuilder?.clearData() },
            R.id.simple_plus to { operationBuilder?.executeOperation(TwoArgumentOperationType.ADDITION) },
            R.id.simple_minus to { operationBuilder?.executeOperation(TwoArgumentOperationType.SUBTRACTION) },
            R.id.simple_multiply to { operationBuilder?.executeOperation(TwoArgumentOperationType.MULTIPLICATION) },
            R.id.simple_divide to { operationBuilder?.executeOperation(TwoArgumentOperationType.DIVISION) },
            R.id.simple_percent to { operationBuilder?.executeOperation(OneArgumentOperationType.PERCENTAGE) },
            R.id.simple_equal to { operationBuilder?.executeOperation(TwoArgumentOperationType.RESULT) }
        )

        operationButtons.forEach { (id, operation) ->
            val button: Button = findViewById(id)
            button.setOnClickListener { operation() }
        }
    }

    private fun numericButtonsHandle() {
        val numericButtons = mapOf(
            R.id.simple_one to '1',
            R.id.simple_two to '2',
            R.id.simple_three to '3',
            R.id.simple_four to '4',
            R.id.simple_five to '5',
            R.id.simple_six to '6',
            R.id.simple_seven to '7',
            R.id.simple_eight to '8',
            R.id.simple_nine to '9',
            R.id.simple_zero to '0',
            R.id.simple_point to '.',
            R.id.simple_sign to '-'
        )

        numericButtons.forEach {(id, digit) ->
            val button: Button = findViewById(id)
            button.setOnClickListener {
                operationBuilder?.appendDigit(digit)
            }
        }
    }
}