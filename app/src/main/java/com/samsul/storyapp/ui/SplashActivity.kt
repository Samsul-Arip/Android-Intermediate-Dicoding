package com.samsul.storyapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.samsul.storyapp.R
import com.samsul.storyapp.utils.PrefsManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupView()
        val prefsManager = PrefsManager(this)
        lifecycleScope.launch {
            delay(2000)
            val intent = if(prefsManager.exampleBoolean) {
                Intent(this@SplashActivity, HomeActivity::class.java)
            } else {
                Intent(this@SplashActivity, WelcomeActivity::class.java)
            }
            startActivity(intent)
            finish()
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}