package com.kelompoksatuandsatu.preducation.presentation.feature.register

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.ActivityRegisterBinding
import com.kelompoksatuandsatu.preducation.model.auth.UserAuth
import com.kelompoksatuandsatu.preducation.model.auth.otp.postemailotp.EmailOtp
import com.kelompoksatuandsatu.preducation.presentation.feature.login.LoginActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.otp.OtpActivity
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

        val loginTextView = binding.loginText
        val loginString = getString(R.string.text_login_here)
        val loginSpannable = SpannableString(loginString)
        loginSpannable.setSpan(
            UnderlineSpan(),
            0,
            loginString.length,
            0
        )
        loginTextView.text = loginSpannable
        loginTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
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
                doOnSuccess = {
                    StyleableToast.makeText(
                        this,
                        getString(R.string.register_successfull),
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
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.signUpButton.isVisible = true
                    binding.signUpButton.isEnabled = true
                    StyleableToast.makeText(
                        this,
                        getString(R.string.register_failed) + it.exception?.message.orEmpty(),
                        R.style.failedtoast
                    ).show()
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
