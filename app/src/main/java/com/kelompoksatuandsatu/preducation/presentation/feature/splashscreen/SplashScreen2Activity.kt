package com.kelompoksatuandsatu.preducation.presentation.feature.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kelompoksatuandsatu.preducation.databinding.ActivitySplashScreen2Binding
import com.kelompoksatuandsatu.preducation.presentation.feature.main.MainActivity

class SplashScreen2Activity : AppCompatActivity() {

    private val binding: ActivitySplashScreen2Binding by lazy {
        ActivitySplashScreen2Binding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvSkip.setOnClickListener {
            navigateToMain()
        }

        binding.clButtonNext.setOnClickListener {
            navigateToSplashScreen3()
        }
    }

    private fun navigateToSplashScreen3() {
        val intent = Intent(this, SplashScreen3Activity::class.java).apply {
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
