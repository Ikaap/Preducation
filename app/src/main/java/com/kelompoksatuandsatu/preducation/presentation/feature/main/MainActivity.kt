package com.kelompoksatuandsatu.preducation.presentation.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.presentation.feature.notifications.NotificationFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = NotificationFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
