package com.ertools.mobile_calculator.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ertools.mobile_calculator.R
import com.ertools.mobile_calculator.model.OperationBuilder
import com.ertools.mobile_calculator.model.OneArgumentOperationType
import com.ertools.mobile_calculator.model.TwoArgumentOperationType

class ScientificCalculatorActivity : AppCompatActivity() {
    private lateinit var label: TextView
    private lateinit var operationBuilder: OperationBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scientific_calculator)
        label = findViewById(R.id.scientific_result)
        label.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_END
        operationBuilder = OperationBuilder(label)
        operationBuilder.clearData()
        numericButtonsHandle()
        operationButtonsHandle()
    }

    private fun operationButtonsHandle() {
        val clearBtn: Button = findViewById(R.id.scientific_clear)
        clearBtn.setOnClickListener {
            operationBuilder.clearData()
        }
        val plusBtn: Button = findViewById(R.id.scientific_plus)
        plusBtn.setOnClickListener {
            operationBuilder.executeOperation(TwoArgumentOperationType.ADDITION)
        }
        val minusBtn: Button = findViewById(R.id.scientific_minus)
        minusBtn.setOnClickListener {
            operationBuilder.executeOperation(TwoArgumentOperationType.SUBTRACTION)
        }
        val multiplyBtn: Button = findViewById(R.id.scientific_multiply)
        multiplyBtn.setOnClickListener {
            operationBuilder.executeOperation(TwoArgumentOperationType.MULTIPLICATION)
        }
        val divideBtn: Button = findViewById(R.id.scientific_divide)
        divideBtn.setOnClickListener {
            operationBuilder.executeOperation(TwoArgumentOperationType.DIVISION)
        }
        val percentageBtn: Button = findViewById(R.id.scientific_percent)
        percentageBtn.setOnClickListener {
            operationBuilder.executeOperation(OneArgumentOperationType.PERCENTAGE)
        }
        val equalBtn: Button = findViewById(R.id.scientific_equal)
        equalBtn.setOnClickListener {
            operationBuilder.executeOperation()
        }
        val squareBtn: Button = findViewById(R.id.scientific_sqrt)
        squareBtn.setOnClickListener {
            operationBuilder.executeOperation(OneArgumentOperationType.SQRT)
        }
        val powerBtn: Button = findViewById(R.id.scientific_power)
        powerBtn.setOnClickListener {
            operationBuilder.executeOperation(TwoArgumentOperationType.POWER)
        }
        val sinBtn: Button = findViewById(R.id.scientific_sin)
        sinBtn.setOnClickListener {
            operationBuilder.executeOperation(OneArgumentOperationType.SIN)
        }
        val cosBtn: Button = findViewById(R.id.scientific_cos)
        cosBtn.setOnClickListener {
            operationBuilder.executeOperation(OneArgumentOperationType.COS)
        }
        val tanBtn: Button = findViewById(R.id.scientific_tan)
        tanBtn.setOnClickListener {
            operationBuilder.executeOperation(OneArgumentOperationType.TAN)
        }
        val logBtn: Button = findViewById(R.id.scientific_log)
        logBtn.setOnClickListener {
            operationBuilder.executeOperation(TwoArgumentOperationType.LOG)
        }
        val lnBtn: Button = findViewById(R.id.scientific_ln)
        lnBtn.setOnClickListener {
            operationBuilder.executeOperation(OneArgumentOperationType.LN)
        }
        val factorialBtn: Button = findViewById(R.id.scientific_factorial)
        factorialBtn.setOnClickListener {
            operationBuilder.executeOperation(OneArgumentOperationType.FACTORIAL)
        }
    }

    private fun numericButtonsHandle() {
        val oneBtn: Button = findViewById(R.id.scientific_one)
        oneBtn.setOnClickListener {
            operationBuilder.appendDigit('1')
        }
        val twoBtn: Button = findViewById(R.id.scientific_two)
        twoBtn.setOnClickListener {
            operationBuilder.appendDigit('2')
        }
        val threeBtn: Button = findViewById(R.id.scientific_three)
        threeBtn.setOnClickListener {
            operationBuilder.appendDigit('3')
        }
        val fourBtn: Button = findViewById(R.id.scientific_four)
        fourBtn.setOnClickListener {
            operationBuilder.appendDigit('4')
        }
        val fiveBtn: Button = findViewById(R.id.scientific_five)
        fiveBtn.setOnClickListener {
            operationBuilder.appendDigit('5')
        }
        val sixBtn: Button = findViewById(R.id.scientific_six)
        sixBtn.setOnClickListener {
            operationBuilder.appendDigit('6')
        }
        val sevenBtn: Button = findViewById(R.id.scientific_seven)
        sevenBtn.setOnClickListener {
            operationBuilder.appendDigit('7')
        }
        val eightBtn: Button = findViewById(R.id.scientific_eight)
        eightBtn.setOnClickListener {
            operationBuilder.appendDigit('8')
        }
        val nineBtn: Button = findViewById(R.id.scientific_nine)
        nineBtn.setOnClickListener {
            operationBuilder.appendDigit('9')
        }
        val zeroBtn: Button = findViewById(R.id.scientific_zero)
        zeroBtn.setOnClickListener {
            operationBuilder.appendDigit('0')
        }
        val pointBtn: Button = findViewById(R.id.scientific_point)
        pointBtn.setOnClickListener {
            operationBuilder.appendDigit('.')
        }
        val signBtn: Button = findViewById(R.id.scientific_sign)
        signBtn.setOnClickListener {
            operationBuilder.appendDigit('-')
        }
    }
}