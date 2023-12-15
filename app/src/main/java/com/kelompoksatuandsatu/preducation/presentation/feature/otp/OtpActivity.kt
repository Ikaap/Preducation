package com.kelompoksatuandsatu.preducation.presentation.feature.otp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.ActivityOtpBinding
import com.otpview.OTPListener
import com.otpview.OTPTextView
import io.github.muddz.styleabletoast.StyleableToast

class OtpActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val otpTextView = findViewById(R.id.otp_view) as OTPTextView
        otpTextView.requestFocusOTP()
        otpTextView.otpListener = object : OTPListener {
            override fun onInteractionListener() {
            }

            override fun onOTPComplete(otp: String) {
                StyleableToast.makeText(
                    this@OtpActivity,
                    "    Registrasi Berhasil    ",
                    R.style.failedtoast
                ).show()
            }
        }
    }
}
