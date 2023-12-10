package com.kelompoksatuandsatu.preducation.presentation.feature.login

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.method.PasswordTransformationMethod
import android.text.style.UnderlineSpan
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.kelompoksatuandsatu.preducation.databinding.ActivityLoginBinding
import com.kelompoksatuandsatu.preducation.model.Login
import com.kelompoksatuandsatu.preducation.presentation.feature.main.MainActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.register.RegisterActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.resetpassword.ResetPasswordActivity

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private lateinit var loginModel: Login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loginModel = Login(
            loginTitle = "Login",
            emailLabel = "Email",
            passwordLabel = "Password",
            forgotLabel = "Forgot Password",
            buttonLabel = "Sign In",
            registerTitle = "Don't have account yet?",
            registerText = "Register Here",
            withoutText = "Enter Without Logging In"
        )

        binding.lifecycleOwner = this
        binding.loginModel = loginModel

        val registerTextView = binding.registerText
        val registerString = loginModel.registerText
        val registerSpannable = SpannableString(registerString)
        registerSpannable.setSpan(
            UnderlineSpan(),
            0,
            registerString.length,
            0
        )
        registerTextView.text = registerSpannable

        // Show & Hide Password
        binding.showHidePasswordButton.setOnClickListener {
            togglePasswordVisibility()
        }

        // Show Message Box
        binding.signInButton.setOnClickListener {
            showErrorMessageBox()
        }

        registerTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.forgotPasswordText.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }
        binding.tvWithoutLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun togglePasswordVisibility() {
        if (binding.passwordInput.transformationMethod == PasswordTransformationMethod.getInstance()) {
            binding.passwordInput.transformationMethod = null
        } else {
            binding.passwordInput.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }

    private fun showErrorMessageBox() {
        binding.errorMessageBox.visibility = LinearLayout.VISIBLE
    }
}
