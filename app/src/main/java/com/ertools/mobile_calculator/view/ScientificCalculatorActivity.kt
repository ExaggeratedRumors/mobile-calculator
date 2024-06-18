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
import com.ertools.mobile_calculator.utils.SCIENTIST_DIGITS_COUNT

class ScientificCalculatorActivity : AppCompatActivity() {
    private lateinit var label: TextView
    private var operationBuilder: OperationBuilder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scientific_calculator)
        label = findViewById(R.id.scientific_result)
        label.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_END
        loadState(savedInstanceState)
        numericButtonsHandle()
        operationButtonsHandle()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(OPERATION_BUILDER_STATE, operationBuilder)
        super.onSaveInstanceState(outState)
    }

    private fun loadState(savedInstanceState: Bundle?) {
        operationBuilder = savedInstanceState
            ?.getSerializable(OPERATION_BUILDER_STATE) as OperationBuilder?
            ?: OperationBuilder()
        operationBuilder?.build(applicationContext, label, SCIENTIST_DIGITS_COUNT)
    }

    private fun operationButtonsHandle() {
        val operationButtons = mapOf(
            R.id.scientific_clear to { operationBuilder?.clearInput() },
            R.id.scientific_clear_all to { operationBuilder?.clearData() },
            R.id.scientific_plus to { operationBuilder?.executeOperation(TwoArgumentOperationType.ADDITION) },
            R.id.scientific_minus to { operationBuilder?.executeOperation(TwoArgumentOperationType.SUBTRACTION) },
            R.id.scientific_multiply to { operationBuilder?.executeOperation(TwoArgumentOperationType.MULTIPLICATION) },
            R.id.scientific_divide to { operationBuilder?.executeOperation(TwoArgumentOperationType.DIVISION) },
            R.id.scientific_percent to { operationBuilder?.executeOperation(OneArgumentOperationType.PERCENTAGE) },
            R.id.scientific_equal to { operationBuilder?.executeOperation(TwoArgumentOperationType.RESULT) },
            R.id.scientific_sqrt to { operationBuilder?.executeOperation(OneArgumentOperationType.SQRT) },
            R.id.scientific_power to { operationBuilder?.executeOperation(TwoArgumentOperationType.POWER) },
            R.id.scientific_double to { operationBuilder?.executeOperation(OneArgumentOperationType.DOUBLE) },
            R.id.scientific_sin to { operationBuilder?.executeOperation(OneArgumentOperationType.SIN) },
            R.id.scientific_cos to { operationBuilder?.executeOperation(OneArgumentOperationType.COS) },
            R.id.scientific_tan to { operationBuilder?.executeOperation(OneArgumentOperationType.TAN) },
            R.id.scientific_log to { operationBuilder?.executeOperation(TwoArgumentOperationType.LOG) },
            R.id.scientific_ln to { operationBuilder?.executeOperation(OneArgumentOperationType.LN) },
            R.id.scientific_factorial to { operationBuilder?.executeOperation(OneArgumentOperationType.FACTORIAL) }
        )

        operationButtons.forEach { (id, operation) ->
            val button: Button = findViewById(id)
            button.setOnClickListener {
                operation()
            }
        }
    }

    private fun numericButtonsHandle() {
        val numericButtons = mapOf(
            R.id.scientific_one to '1',
            R.id.scientific_two to '2',
            R.id.scientific_three to '3',
            R.id.scientific_four to '4',
            R.id.scientific_five to '5',
            R.id.scientific_six to '6',
            R.id.scientific_seven to '7',
            R.id.scientific_eight to '8',
            R.id.scientific_nine to '9',
            R.id.scientific_zero to '0',
            R.id.scientific_point to '.',
            R.id.scientific_sign to '-'
        )

        numericButtons.forEach { (id, digit) ->
            val button: Button = findViewById(id)
            button.setOnClickListener {
                operationBuilder?.appendDigit(digit)
            }
        }
    }
}