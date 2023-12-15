package com.kelompoksatuandsatu.preducation.presentation.feature.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kelompoksatuandsatu.preducation.databinding.ActivitySplashScreenTwoBinding

class SplashScreenTwoActivity : AppCompatActivity() {

    private val binding: ActivitySplashScreenTwoBinding by lazy {
        ActivitySplashScreenTwoBinding.inflate(layoutInflater)
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
            navigateToSplashScreenThree()
        }
    }

    private fun navigateToSplashScreenThree() {
        val intent = Intent(this, SplashScreenThreeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun navigateToMain() {
        val intent = Intent(this, SplashScreenThreeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }
}
