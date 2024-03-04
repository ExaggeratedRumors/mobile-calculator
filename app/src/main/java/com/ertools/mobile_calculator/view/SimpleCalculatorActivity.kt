package com.ertools.mobile_calculator.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ertools.mobile_calculator.R
import com.ertools.mobile_calculator.model.OneArgumentOperationType
import com.ertools.mobile_calculator.model.OperationBuilder
import com.ertools.mobile_calculator.model.TwoArgumentOperationType

class SimpleCalculatorActivity : AppCompatActivity() {
    private lateinit var label: TextView
    private lateinit var operationBuilder: OperationBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_calculator)
        label = findViewById(R.id.simple_result)
        label.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_END
        operationBuilder = OperationBuilder(label)
        operationBuilder.clearData()
        numericButtonsHandle()
        operationButtonsHandle()
    }

    private fun operationButtonsHandle() {
        val clearBtn: Button = findViewById(R.id.simple_clear)
        clearBtn.setOnClickListener {
            operationBuilder.clearData()
        }
        val backBtn: Button = findViewById(R.id.simple_back)
        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val plusBtn: Button = findViewById(R.id.simple_plus)
        plusBtn.setOnClickListener {
            operationBuilder.executeOperation(TwoArgumentOperationType.ADDITION)
        }
        val minusBtn: Button = findViewById(R.id.simple_minus)
        minusBtn.setOnClickListener {
            operationBuilder.executeOperation(TwoArgumentOperationType.SUBTRACTION)
        }
        val multiplyBtn: Button = findViewById(R.id.simple_multiply)
        multiplyBtn.setOnClickListener {
            operationBuilder.executeOperation(TwoArgumentOperationType.MULTIPLICATION)
        }
        val divideBtn: Button = findViewById(R.id.simple_divide)
        divideBtn.setOnClickListener {
            operationBuilder.executeOperation(TwoArgumentOperationType.DIVISION)
        }
        val percentageBtn: Button = findViewById(R.id.simple_percent)
        percentageBtn.setOnClickListener {
            operationBuilder.executeOperation(OneArgumentOperationType.PERCENTAGE)
        }
        val equalBtn: Button = findViewById(R.id.simple_equal)
        equalBtn.setOnClickListener {
            operationBuilder.executeOperation()
        }
    }

    private fun numericButtonsHandle() {
        val oneBtn: Button = findViewById(R.id.simple_one)
        oneBtn.setOnClickListener {
            operationBuilder.appendDigit('1')
        }
        val twoBtn: Button = findViewById(R.id.simple_two)
        twoBtn.setOnClickListener {
            operationBuilder.appendDigit('2')
        }
        val threeBtn: Button = findViewById(R.id.simple_three)
        threeBtn.setOnClickListener {
            operationBuilder.appendDigit('3')
        }
        val fourBtn: Button = findViewById(R.id.simple_four)
        fourBtn.setOnClickListener {
            operationBuilder.appendDigit('4')
        }
        val fiveBtn: Button = findViewById(R.id.simple_five)
        fiveBtn.setOnClickListener {
            operationBuilder.appendDigit('5')
        }
        val sixBtn: Button = findViewById(R.id.simple_six)
        sixBtn.setOnClickListener {
            operationBuilder.appendDigit('6')
        }
        val sevenBtn: Button = findViewById(R.id.simple_seven)
        sevenBtn.setOnClickListener {
            operationBuilder.appendDigit('7')
        }
        val eightBtn: Button = findViewById(R.id.simple_eight)
        eightBtn.setOnClickListener {
            operationBuilder.appendDigit('8')
        }
        val nineBtn: Button = findViewById(R.id.simple_nine)
        nineBtn.setOnClickListener {
            operationBuilder.appendDigit('9')
        }
        val zeroBtn: Button = findViewById(R.id.simple_zero)
        zeroBtn.setOnClickListener {
            operationBuilder.appendDigit('0')
        }
        val pointBtn: Button = findViewById(R.id.simple_point)
        pointBtn.setOnClickListener {
            operationBuilder.appendDigit('.')
        }
        val signBtn: Button = findViewById(R.id.simple_sign)
        signBtn.setOnClickListener {
            operationBuilder.appendDigit('-')
        }
    }
}