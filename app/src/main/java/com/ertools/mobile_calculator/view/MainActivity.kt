package com.ertools.mobile_calculator.view

import android.content.Intent
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
            val intent = Intent(this, SimpleCalculatorActivity::class.java)
            startActivity(intent)
        }
    }

    private fun scientificCalculatorHandle() {
        this.scientificCalculatorButton = findViewById(R.id.scientific_calculator_btn)
        scientificCalculatorButton.setOnClickListener {
            val intent = Intent(this, ScientificCalculatorActivity::class.java)
            startActivity(intent)
        }
    }

    private fun creditsHandle() {
        this.creditsButton = findViewById(R.id.credits_btn)
        creditsButton.setOnClickListener {
            val intent = Intent(this, CreditsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun exitHandle() {
        this.exitButton = findViewById(R.id.exit_btn)
        exitButton.setOnClickListener {
            this.finishAffinity()
        }
    }
}

