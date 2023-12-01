package com.kelompoksatuandsatu.preducation.presentation.feature.otp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kelompoksatuandsatu.preducation.R
import com.otpview.OTPListener
import com.otpview.OTPTextView
import io.github.muddz.styleabletoast.StyleableToast

class OtpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        val otpTextView = findViewById(R.id.otp_view) as OTPTextView
        otpTextView.requestFocusOTP()
        otpTextView.otpListener = object : OTPListener {
            override fun onInteractionListener() {
            }

            override fun onOTPComplete(otp: String) {
                StyleableToast.makeText(
                    this@OtpActivity,
                    "    Registrasi Berhasil    ",
                    R.style.failedinputotp
                ).show()
            }
        }
    }
}
