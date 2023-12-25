package com.kelompoksatuandsatu.preducation.presentation.feature.main

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var isKeyboardVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupBottomNav()

        val rootView = findViewById<View>(android.R.id.content)
        val observer: ViewTreeObserver = rootView.viewTreeObserver
        observer.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                val heightDiff = rootView.rootView.height - rootView.height
                if (heightDiff > 200) {
                    if (!isKeyboardVisible) {
                        isKeyboardVisible = true
                        binding.bottomNavView.visibility = View.GONE
                    }
                } else {
                    if (isKeyboardVisible) {
                        isKeyboardVisible = false
                        binding.bottomNavView.visibility = View.VISIBLE
                    }
                }
                return true
            }
        })
    }

    private fun setupBottomNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.bottomNavView.setupWithNavController(navController)
    }
}
