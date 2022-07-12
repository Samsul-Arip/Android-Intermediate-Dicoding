package com.samsul.storyapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.samsul.storyapp.R
import com.samsul.storyapp.utils.PrefsManager


class WelcomeActivity : AppCompatActivity() {

    private lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        prefsManager = PrefsManager(this)
        supportActionBar?.hide()
        val btnStart = findViewById<Button>(R.id.btn_start)
        btnStart.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}