package com.kelompoksatuandsatu.preducation.presentation.feature.filter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kelompoksatuandsatu.preducation.databinding.ActivityFilterBinding

class FilterActivity : AppCompatActivity() {

    private val binding: ActivityFilterBinding by lazy {
        ActivityFilterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ivArrowLeft.setOnClickListener {
            onBackPressed()
        }
    }
}
