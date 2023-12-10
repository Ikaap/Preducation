package com.kelompoksatuandsatu.preducation.presentation.feature.register

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.method.PasswordTransformationMethod
import android.text.style.UnderlineSpan
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.kelompoksatuandsatu.preducation.databinding.ActivityRegisterBinding
import com.kelompoksatuandsatu.preducation.model.Register
import com.kelompoksatuandsatu.preducation.presentation.feature.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private lateinit var registerModel: Register

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        registerModel = Register(
            registerTitle = "Getting Started.!",
            nameLabel = "Name",
            emailLabel = "Email",
            phoneLabel = "Phone Number",
            passwordLabel = "Create a Password",
            buttonLabel = "Sign Up",
            loginTitle = "Already have an account?",
            loginText = "Login Here"
        )

        binding.lifecycleOwner = this
        binding.registerModel = registerModel

        val loginTextView = binding.loginText
        val loginString = registerModel.loginText
        val loginSpannable = SpannableString(loginString)
        loginSpannable.setSpan(
            UnderlineSpan(),
            0,
            loginString.length,
            0
        )
        loginTextView.text = loginSpannable

        // Show & Hide Password
        binding.showHidePasswordButton.setOnClickListener {
            togglePasswordVisibility()
        }

        // Show Message Box
        binding.signUpButton.setOnClickListener {
            showErrorMessageBox()
        }

        loginTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
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
