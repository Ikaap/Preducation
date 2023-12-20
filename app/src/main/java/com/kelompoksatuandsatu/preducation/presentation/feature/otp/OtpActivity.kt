package com.kelompoksatuandsatu.preducation.presentation.feature.otp

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.ActivityOtpBinding
import com.kelompoksatuandsatu.preducation.databinding.LayoutDialogSuccesOtpBinding
import com.kelompoksatuandsatu.preducation.model.auth.otp.postemailotp.EmailOtp
import com.kelompoksatuandsatu.preducation.model.auth.otp.verifyotp.OtpData
import com.kelompoksatuandsatu.preducation.presentation.feature.login.LoginActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.register.RegisterActivity
import com.kelompoksatuandsatu.preducation.utils.exceptions.ApiException
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import com.otpview.OTPListener
import io.github.muddz.styleabletoast.StyleableToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class OtpActivity : AppCompatActivity() {

    private val binding: ActivityOtpBinding by lazy {
        ActivityOtpBinding.inflate(layoutInflater)
    }
    private val viewModel: OtpViewModel by viewModel()

    private var dataOtp = OtpData("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getDataEmail()
        inputOtp()
        observe()
        setOnClickListener()
    }

    private fun getDataEmail() {
        val emailData = intent.getParcelableExtra<EmailOtp>("EMAIL_OTP")
        emailData?.let { dataRequest ->
            binding.tvUserEmail.text = dataRequest.email
        }
    }

    private fun inputOtp() {
        binding.otpView.requestFocusOTP()
        binding.otpView.otpListener = object : OTPListener {
            override fun onInteractionListener() {
            }

            override fun onOTPComplete(otp: String) {
                dataOtp = OtpData(
                    otp
                )
                viewModel.userOtp(dataOtp)
            }
        }
    }

    private fun observe() {
        viewModel.otpResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    it.payload?.let { response ->
                        StyleableToast.makeText(
                            this,
                            response.message,
                            R.style.successtoast
                        ).show()
                        binding.otpView.showSuccess()
                        showDialog()
                    }
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                },
                doOnError = {
                    binding.pbLoading.isVisible = false

                    binding.otpView.showError()

                    if (it.exception is ApiException) {
                        if (it.exception.httpCode == 400) {
                            StyleableToast.makeText(
                                this,
                                it.exception.getParsedError()?.message,
                                R.style.failedtoast
                            ).show()
                            if (it.exception.getParsedError()?.message?.contains("expired") == true) {
                                StyleableToast.makeText(
                                    this,
                                    it.exception.getParsedError()?.message + "Please ask for the OTP code again",
                                    R.style.failedtoast
                                ).show()
                            }
                        }
                    }

                    binding.otpView.resetState()
                }
            )
        }
    }

    private fun setOnClickListener() {
        binding.ivArrowLeft.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.tvResendOTPCodeVieEmail.setOnClickListener {
            val emailData = intent.getParcelableExtra<EmailOtp>("EMAIL_OTP")
            emailData?.let { dataRequest ->
                viewModel.postEmailOtp(dataRequest)
            }
        }
    }

    private fun showDialog() {
        val binding: LayoutDialogSuccesOtpBinding =
            LayoutDialogSuccesOtpBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this, 0).create()

        dialog.apply {
            setView(binding.root)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }.show()

        binding.ivButtonNext.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
