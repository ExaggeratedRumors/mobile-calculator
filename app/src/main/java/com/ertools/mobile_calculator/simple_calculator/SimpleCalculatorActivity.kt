package com.ertools.mobile_calculator.simple_calculator

import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ertools.mobile_calculator.R
import com.ertools.mobile_calculator.computing.*
import java.lang.StringBuilder

class SimpleCalculatorActivity : AppCompatActivity() {
    private var firstArgument = StringBuilder()
    private var secondArgument = StringBuilder()
    private lateinit var label: TextView
    private var operation : ((Double, Double) -> Double?)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_calculator)
        label = findViewById(R.id.simple_result)
        operationButtonsHandle()
        numericButtonsHandle()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(operation == null) label.text = firstArgument.toString()
        else label.text = secondArgument.toString()
        return super.onTouchEvent(event)
    }

    private fun operationButtonsHandle() {
        val plusBtn: Button = findViewById(R.id.simple_plus)
        plusBtn.setOnClickListener {
            operation = (::add)
        }
        val minusBtn: Button = findViewById(R.id.simple_minus)
        minusBtn.setOnClickListener {
            operation = (::subtract)
        }
        val multiplyBtn: Button = findViewById(R.id.simple_multiply)
        multiplyBtn.setOnClickListener {
            operation = (::multiply)
        }
        val divideBtn: Button = findViewById(R.id.simple_divide)
        divideBtn.setOnClickListener {
            operation = (::divide)
        }
        val percentageBtn: Button = findViewById(R.id.simple_percent)
        percentageBtn.setOnClickListener {
            operation = (::percent)
        }
        val signBtn: Button = findViewById(R.id.simple_sign)
        signBtn.setOnClickListener {
            operation = (::negate)
        }
        val reciprocalBtn: Button = findViewById(R.id.simple_point)
        reciprocalBtn.setOnClickListener {
            if(operation == null) {
                firstArgument.append(".")
            } else {
                secondArgument.append(".")
            }
        }
        val equalBtn: Button = findViewById(R.id.simple_equal)
        equalBtn.setOnClickListener {
            if(firstArgument.isNotEmpty() && secondArgument.isNotEmpty()) {
                val result = operation?.invoke(
                    firstArgument.toString().toDouble(),
                    secondArgument.toString().toDouble()
                )
                firstArgument.clear()
                secondArgument.clear()
                operation = null
                if(result != null) firstArgument.append(result)
            }
        }
    }

    private fun numericButtonsHandle() {
        val oneBtn: Button = findViewById(R.id.simple_one)
        oneBtn.setOnClickListener {
            if(operation == null) firstArgument.append("1")
            else secondArgument.append("1")
        }
        val twoBtn: Button = findViewById(R.id.simple_two)
        twoBtn.setOnClickListener {
            if(operation == null) firstArgument.append("2")
            else secondArgument.append("2")
        }
        val threeBtn: Button = findViewById(R.id.simple_three)
        threeBtn.setOnClickListener {
            if(operation == null) firstArgument.append("3")
            else secondArgument.append("3")
        }
        val fourBtn: Button = findViewById(R.id.simple_four)
        fourBtn.setOnClickListener {
            if(operation == null) firstArgument.append("4")
            else secondArgument.append("4")
        }
        val fiveBtn: Button = findViewById(R.id.simple_five)
        fiveBtn.setOnClickListener {
            if(operation == null) firstArgument.append("5")
            else secondArgument.append("5")
        }
        val sixBtn: Button = findViewById(R.id.simple_six)
        sixBtn.setOnClickListener {
            if(operation == null) firstArgument.append("6")
            else secondArgument.append("6")
        }
        val sevenBtn: Button = findViewById(R.id.simple_seven)
        sevenBtn.setOnClickListener {
            if(operation == null) firstArgument.append("7")
            else secondArgument.append("7")
        }
        val eightBtn: Button = findViewById(R.id.simple_eight)
        eightBtn.setOnClickListener {
            if(operation == null) firstArgument.append("8")
            else secondArgument.append("8")
        }
        val nineBtn: Button = findViewById(R.id.simple_nine)
        nineBtn.setOnClickListener {
            if(operation == null) firstArgument.append("9")
            else secondArgument.append("9")
        }
        val zeroBtn: Button = findViewById(R.id.simple_zero)
        zeroBtn.setOnClickListener {
            if(operation == null && firstArgument.isNotEmpty())
                firstArgument.append("0")
            else if(secondArgument.isNotEmpty())
                secondArgument.append("0")
        }
    }
}