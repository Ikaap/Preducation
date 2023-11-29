package com.kelompoksatuandsatu.preducation.presentation.feature.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kelompoksatuandsatu.preducation.databinding.ActivitySplashScreenOneBinding
import com.kelompoksatuandsatu.preducation.presentation.feature.main.MainActivity

class SplashScreenOneActivity : AppCompatActivity() {

    private val binding: ActivitySplashScreenOneBinding by lazy {
        ActivitySplashScreenOneBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.tvSkip.setOnClickListener {
            navigateToMain()
        }

        binding.clButtonNext.setOnClickListener {
            navigateToSplashScreenTwo()
        }
    }

    private fun navigateToSplashScreenTwo() {
        val intent = Intent(this, SplashScreenTwoActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }
}