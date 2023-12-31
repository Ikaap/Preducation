package com.kelompoksatuandsatu.preducation.presentation.feature.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kelompoksatuandsatu.preducation.databinding.ActivitySplashScreenBinding
import com.kelompoksatuandsatu.preducation.presentation.feature.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenActivity : AppCompatActivity() {

    private val binding: ActivitySplashScreenBinding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    private val viewModel: SplashScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getData()
        observeData()
    }

    private fun getData() {
        viewModel.checkLogin()
    }

    private fun observeData() {
        viewModel.isUserLogin.observe(this) { isLogin ->
            if (!isLogin) {
                lifecycleScope.launch {
                    delay(1500)
                    navigateToSplashScreenOne()
                }
            } else {
                lifecycleScope.launch {
                    delay(1500)
                    navigateToHome()
                }
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun navigateToSplashScreenOne() {
        val intent = Intent(this, SplashScreenOneActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }
}
