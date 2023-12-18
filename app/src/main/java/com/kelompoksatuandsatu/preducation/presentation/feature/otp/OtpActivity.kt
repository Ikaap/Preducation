package com.kelompoksatuandsatu.preducation.presentation.feature.otp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.ActivityOtpBinding
import com.kelompoksatuandsatu.preducation.presentation.feature.main.MainActivity
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import com.otpview.OTPListener
import com.otpview.OTPTextView
import io.github.muddz.styleabletoast.StyleableToast
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class OtpActivity : AppCompatActivity() {

    private val binding : ActivityOtpBinding by lazy {
        ActivityOtpBinding.inflate(layoutInflater)
    }
    private val viewModel: OtpViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val otpTextView = findViewById(R.id.otp_view) as OTPTextView
        otpTextView.requestFocusOTP()
        otpTextView.otpListener = object : OTPListener {
            override fun onInteractionListener() {
            }

            override fun onOTPComplete(otp: String) {
                verifyOtp(otp)
            }
        }
    }

    private fun verifyOtp(otp: String) {
        viewModel.otpResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    StyleableToast.makeText(
                        this,
                        getString(R.string.register_successfull),
                        R.style.successtoast
                    ).show()
                    navigateToMain()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    StyleableToast.makeText(
                        this,
                        getString(R.string.register_failed) + it.exception?.message.orEmpty(),
                        R.style.failedtoast
                    ).show()
                }
            )
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
