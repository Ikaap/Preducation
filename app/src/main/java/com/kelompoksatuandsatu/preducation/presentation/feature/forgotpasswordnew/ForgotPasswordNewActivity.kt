package com.kelompoksatuandsatu.preducation.presentation.feature.forgotpasswordnew

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.ActivityForgotPasswordNewBinding
import com.kelompoksatuandsatu.preducation.model.auth.forgotpassword.UserForgotPassword
import com.kelompoksatuandsatu.preducation.presentation.feature.login.LoginActivity
import com.kelompoksatuandsatu.preducation.utils.exceptions.ApiException
import com.kelompoksatuandsatu.preducation.utils.exceptions.NoInternetException
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import io.github.muddz.styleabletoast.StyleableToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordNewActivity : AppCompatActivity() {

    private val binding: ActivityForgotPasswordNewBinding by lazy {
        ActivityForgotPasswordNewBinding.inflate(layoutInflater)
    }

    private val viewModel: ForgotPasswordNewViewModel by viewModel()

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
            viewModel.userForgotPassword(UserForgotPassword(email))
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
                    navigateToLogin()
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
                    if (it.exception is ApiException) {
                        if (it.exception.getParsedErrorForgetPassword()?.success == false) {
                            if (it.exception.httpCode == 500) {
                                binding.layoutCommonState.clErrorState.isGone = false
                                binding.layoutCommonState.ivErrorState.isGone = false
                                binding.layoutCommonState.ivErrorState.setImageResource(R.drawable.img_server_error)
                                StyleableToast.makeText(
                                    this,
                                    getString(R.string.text_sorry_there_s_an_error_on_the_server),
                                    R.style.failedtoast
                                ).show()
                            } else if (it.exception.getParsedErrorForgetPassword()?.success == false) {
                                binding.layoutCommonState.tvError.text =
                                    it.exception.getParsedErrorForgetPassword()?.message
                                StyleableToast.makeText(
                                    this,
                                    it.exception.getParsedErrorForgetPassword()?.message,
                                    R.style.failedtoast
                                ).show()
                            }
                        }
                    } else if (it.exception is NoInternetException) {
                        if (!it.exception.isNetworkAvailable(this)) {
                            binding.layoutCommonState.clErrorState.isGone = false
                            binding.layoutCommonState.ivErrorState.isGone = false
                            binding.layoutCommonState.ivErrorState.setImageResource(R.drawable.img_no_connection)
                            StyleableToast.makeText(
                                this,
                                getString(R.string.text_no_internet_connection),
                                R.style.failedtoast
                            ).show()
                        }
                    }
                }
            )
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
