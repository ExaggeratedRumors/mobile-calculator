package com.ertools.mobile_calculator.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.ertools.mobile_calculator.R

class MainActivity : AppCompatActivity() {
    private lateinit var simpleCalculatorButton: Button
    private lateinit var scientificCalculatorButton: Button
    private lateinit var creditsButton: Button
    private lateinit var exitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        simpleCalculatorHandle()
        scientificCalculatorHandle()
        creditsHandle()
        exitHandle()
    }

    private fun simpleCalculatorHandle() {
        this.simpleCalculatorButton = findViewById(R.id.simple_calculator_btn)
        simpleCalculatorButton.setOnClickListener {
            setContentView(R.layout.activity_simple_calculator)
        }
    }

    private fun scientificCalculatorHandle() {
        this.scientificCalculatorButton = findViewById(R.id.scientific_calculator_btn)
        scientificCalculatorButton.setOnClickListener {
            setContentView(R.layout.activity_scientific_calculator)
        }
    }

    private fun creditsHandle() {
        this.creditsButton = findViewById(R.id.credits_btn)
        creditsButton.setOnClickListener {
            finish()
        }
    }

    private fun exitHandle() {
        this.exitButton = findViewById(R.id.exit_btn)
        exitButton.setOnClickListener {
            setContentView(R.layout.activity_credits)
        }
    }
}