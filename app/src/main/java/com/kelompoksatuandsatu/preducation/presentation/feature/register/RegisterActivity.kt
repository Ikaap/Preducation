package com.kelompoksatuandsatu.preducation.presentation.feature.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.ActivityRegisterBinding
import com.kelompoksatuandsatu.preducation.model.auth.UserAuth
import com.kelompoksatuandsatu.preducation.model.auth.otp.postemailotp.EmailOtp
import com.kelompoksatuandsatu.preducation.presentation.feature.login.LoginActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.otp.OtpActivity
import com.kelompoksatuandsatu.preducation.utils.exceptions.ApiException
import com.kelompoksatuandsatu.preducation.utils.exceptions.NoInternetException
import com.kelompoksatuandsatu.preducation.utils.highLightWord
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import io.github.muddz.styleabletoast.StyleableToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel: RegisterViewModel by viewModel()

    private var emailOtp = EmailOtp("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListeners()
        observeResult()
    }

    private fun setClickListeners() {
        binding.signUpButton.setOnClickListener {
            doRegister()
        }

        binding.loginText.highLightWord(getString(R.string.text_login_here)) {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
    }

    private fun doRegister() {
        if (isFormValid()) {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val numberPhone = binding.etPhoneNumber.text.toString().trim()

            val userAuth = UserAuth(
                email,
                name,
                numberPhone,
                password
            )

            emailOtp = EmailOtp(
                email
            )
            viewModel.userRegister(userAuth)
        }
    }

    private fun isFormValid(): Boolean {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val numberPhone = binding.etPhoneNumber.text.toString().trim()

        return checkNameValidation(name) && checkEmailValidation(email) &&
            checkPasswordValidation(password) && checkNumberPhoneValidation(numberPhone)
    }

    private fun checkNameValidation(name: String): Boolean {
        return if (name.isEmpty()) {
            binding.tilName.isErrorEnabled = true
            binding.tilName.error = getString(R.string.text_error_name_cannot_empy)
            binding.etName.setBackgroundResource(R.drawable.bg_edit_text_error)
            false
        } else {
            binding.tilName.isErrorEnabled = false
            true
        }
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

    private fun checkPasswordValidation(
        password: String
    ): Boolean {
        return if (password.isEmpty()) {
            binding.tilPassword.isErrorEnabled = true
            binding.tilPassword.error = getString(R.string.text_sorry_wrong_password)
            binding.etPassword.setBackgroundResource(R.drawable.bg_edit_text_error)
            false
        } else if (password.length < 8) {
            binding.tilPassword.isErrorEnabled = true
            binding.tilPassword.error = getString(R.string.text_password_min_8_character)
            binding.etPassword.setBackgroundResource(R.drawable.bg_edit_text_error)
            false
        } else {
            binding.tilPassword.isErrorEnabled = false
            true
        }
    }

    private fun checkNumberPhoneValidation(
        numberPhone: String
    ): Boolean {
        return if (numberPhone.isEmpty()) {
            binding.tilPhoneNumber.isErrorEnabled = true
            binding.tilPhoneNumber.error = getString(R.string.text_number_phone_empty)
            binding.etPhoneNumber.setBackgroundResource(R.drawable.bg_edit_text_error)
            false
        } else if (numberPhone.length in 14..10) {
            binding.tilPhoneNumber.isErrorEnabled = true
            binding.tilPhoneNumber.error = getString(R.string.text_number_phone_invalid)
            binding.etPhoneNumber.setBackgroundResource(R.drawable.bg_edit_text_error)
            false
        } else {
            binding.tilPhoneNumber.isErrorEnabled = false
            true
        }
    }

    private fun observeResult() {
        viewModel.registerResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = { resultWrapper ->
                    val response = resultWrapper.payload
                    StyleableToast.makeText(
                        this,
                        "${response?.message}",
                        R.style.successtoast
                    ).show()

                    it.payload?.let {
                        viewModel.saveIdUser(it.data.id.orEmpty())
                        Toast.makeText(this, "user id : ${it.data.id}", Toast.LENGTH_SHORT).show()
                    }
                    navigateToOtp()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.signUpButton.isVisible = false
                },
                doOnError = { resultWrapper ->
                    binding.pbLoading.isVisible = false
                    binding.signUpButton.isVisible = true
                    binding.signUpButton.isEnabled = true

                    val apiException = resultWrapper.exception as? ApiException
                    val message = apiException?.getParsedError()?.message.orEmpty()
                    StyleableToast.makeText(
                        this,
                        "$message",
                        R.style.failedtoast
                    ).show()
                    if (it.exception is ApiException) {
                        if (it.exception.getParsedErrorResister()?.success == false) {
                            if (it.exception.httpCode == 500) {
                                binding.layoutCommonState.clServerError.isGone = false
                                binding.layoutCommonState.ivServerError.isGone = false
                                StyleableToast.makeText(
                                    this,
                                    "SERVER ERROR",
                                    R.style.failedtoast
                                ).show()
                            } else if (it.exception.getParsedErrorResister()?.success == false) {
                                binding.layoutCommonState.tvError.text =
                                    it.exception.getParsedErrorResister()?.message
                                StyleableToast.makeText(
                                    this,
                                    it.exception.getParsedErrorResister()?.message,
                                    R.style.failedtoast
                                ).show()
                            }
                        }
                    } else if (it.exception is NoInternetException) {
                        if (!it.exception.isNetworkAvailable(this)) {
                            binding.layoutCommonState.clNoConnection.isGone = false
                            binding.layoutCommonState.ivNoConnection.isGone = false
                            StyleableToast.makeText(
                                this,
                                "tidak ada internet",
                                R.style.failedtoast
                            ).show()
                        }
                    }
                }
            )
        }
    }

    private fun navigateToOtp() {
        val intent = Intent(this, OtpActivity::class.java)
        intent.putExtra("EMAIL_OTP", emailOtp)
        startActivity(intent)
    }
}
