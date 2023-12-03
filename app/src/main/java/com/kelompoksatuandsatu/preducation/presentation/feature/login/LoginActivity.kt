package com.kelompoksatuandsatu.preducation.presentation.feature.login

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.method.PasswordTransformationMethod
import android.text.style.UnderlineSpan
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.ActivityLoginBinding
import com.kelompoksatuandsatu.preducation.presentation.feature.main.MainActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.register.RegisterActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.resetpassword.ResetPasswordActivity

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private lateinit var passwordInput: EditText
    private lateinit var showHideButtonPassword: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        val registerTextView = findViewById<TextView>(R.id.registerText)
        val registerTextView = binding.registerText
        val registerString = " Register Here"
        val registerSpannable = SpannableString(registerString)
        registerSpannable.setSpan(
            UnderlineSpan(),
            0,
            registerString.length,
            0
        )
        registerTextView.text = registerSpannable

        // Show & Hide Password
        passwordInput = findViewById(R.id.passwordInput)
        showHideButtonPassword = findViewById(R.id.showHidePasswordButton)

        showHideButtonPassword.setOnClickListener {
            togglePasswordVisibility()
        }

        // Show Message Box
        val loginButton = findViewById<ConstraintLayout>(R.id.cl_button_sign_in)
        loginButton.setOnClickListener {
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
        if (passwordInput.transformationMethod == PasswordTransformationMethod.getInstance()) {
            passwordInput.transformationMethod = null
        } else {
            passwordInput.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }

    private fun showErrorMessageBox() {
        val errorMessageBox = findViewById<LinearLayout>(R.id.errorMessageBox)
        errorMessageBox.visibility = LinearLayout.VISIBLE
    }
}
