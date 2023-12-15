package com.kelompoksatuandsatu.preducation.presentation.feature.forgotpassword

import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.ActivityForgotPasswordBinding
import com.kelompoksatuandsatu.preducation.model.auth.ForgotPassword
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import io.github.muddz.styleabletoast.StyleableToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordActivity : AppCompatActivity() {

    private val binding: ActivityForgotPasswordBinding by lazy {
        ActivityForgotPasswordBinding.inflate(layoutInflater)
    }

    private val viewModel: ForgotPasswordViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListeners()
        observeResult()
    }

    private fun setClickListeners() {
        binding.clButtonSendEmail.setOnClickListener {
            doUserForgotPassword()
        }
    }

    private fun doUserForgotPassword() {
        if (isFormValid()) {
            val email = binding.etEmail.text.toString().trim()
            viewModel.userForgotPassword(ForgotPassword(email))
        }
    }

    private fun isFormValid(): Boolean {
        val email = binding.etEmail.text.toString().trim()
        return checkEmailValidation(email)
    }

    private fun checkEmailValidation(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.tilEmail.isErrorEnabled = true
            binding.tilEmail.error = getString(R.string.text_error_email_empty)
            binding.etEmail.setBackgroundResource(R.drawable.bg_edit_text_error)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.isErrorEnabled = true
            binding.tilEmail.error = getString(R.string.text_error_email_invalid)
            binding.etEmail.setBackgroundResource(R.drawable.bg_edit_text_error)
            false
        } else {
            binding.tilEmail.isErrorEnabled = false
            true
        }
    }

    private fun observeResult() {
        viewModel.forgotPasswordResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.clButtonSendEmail.isVisible = true
                    StyleableToast.makeText(
                        this,
                        getString(R.string.reset_password_successful),
                        R.style.successtoast
                    ).show()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.clButtonSendEmail.isVisible = false
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.clButtonSendEmail.isVisible = true
                    binding.clButtonSendEmail.isEnabled = true
                    StyleableToast.makeText(
                        this,
                        getString(R.string.invalid_or_expired_link) + "${it.exception?.message.orEmpty()}",
                        R.style.failedtoast
                    ).show()
                }
            )
        }
    }
}
