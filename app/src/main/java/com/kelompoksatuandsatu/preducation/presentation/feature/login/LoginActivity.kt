package com.kelompoksatuandsatu.preducation.presentation.feature.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.ActivityLoginBinding
import com.kelompoksatuandsatu.preducation.model.auth.UserLogin
import com.kelompoksatuandsatu.preducation.presentation.feature.forgotpasswordnew.ForgotPasswordNewActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.main.MainActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.register.RegisterActivity
import com.kelompoksatuandsatu.preducation.utils.exceptions.ApiException
import com.kelompoksatuandsatu.preducation.utils.exceptions.NoInternetException
import com.kelompoksatuandsatu.preducation.utils.highLightWord
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import io.github.muddz.styleabletoast.StyleableToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListeners()
        observeResult()
    }

    private fun setClickListeners() {
        binding.signInButton.setOnClickListener {
            doLogin()
        }

        binding.registerText.highLightWord(getString(R.string.text_register_here)) {
            navigateToRegister()
        }

        binding.tvWithoutLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.forgotPasswordText.setOnClickListener {
            navigateToForgotPass()
        }
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
    }

    private fun navigateToForgotPass() {
        val intent = Intent(this, ForgotPasswordNewActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
    }

    private fun doLogin() {
        if (isFormValid()) {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            val userAuth = UserLogin(
                email,
                password
            )
            viewModel.userLogin(userAuth)
        }
    }

    private fun isFormValid(): Boolean {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        return checkEmailValidation(email) &&
            checkPasswordValidation(password)
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

    private fun observeResult() {
        viewModel.loginResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    StyleableToast.makeText(
                        this,
                        "Login Successful",
                        R.style.successtoast
                    ).show()
                    it.payload?.let {
                        viewModel.saveIdUser(it.data.id)
                    }
                    navigateToMain()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.signInButton.isVisible = false
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.signInButton.isVisible = true
                    binding.signInButton.isEnabled = true

                    if (it.exception is ApiException) {
                        if (it.exception.httpCode == 400) {
                            StyleableToast.makeText(
                                this,
                                it.exception.getParsedErrorLogin()?.message,
                                R.style.failedtoast
                            ).show()
                        } else if (it.exception.httpCode == 404) {
                            StyleableToast.makeText(
                                this,
                                it.exception.getParsedErrorLogin()?.message,
                                R.style.failedtoast
                            ).show()
                        } else if (it.exception.httpCode == 500) {
                            StyleableToast.makeText(
                                this,
                                "Server Error",
                                R.style.failedtoast
                            ).show()
                        }
                    } else if (it.exception is NoInternetException) {
                        if (!it.exception.isNetworkAvailable(this)) {
                            StyleableToast.makeText(
                                this,
                                "No Internet",
                                R.style.failedtoast
                            ).show()
                        }
                    }
                }
            )
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
