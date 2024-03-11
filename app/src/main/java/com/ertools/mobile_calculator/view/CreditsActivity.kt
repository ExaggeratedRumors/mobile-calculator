package com.ertools.mobile_calculator.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ertools.mobile_calculator.R
import com.ertools.mobile_calculator.utils.GITHUB_URL

class CreditsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credits)
        linkButtonHandle()
    }

    private fun linkButtonHandle() {
        val creditsLinkButton: Button = findViewById(R.id.credits_link_btn)
        creditsLinkButton.setOnClickListener {
            val uri = Uri.parse(GITHUB_URL)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}