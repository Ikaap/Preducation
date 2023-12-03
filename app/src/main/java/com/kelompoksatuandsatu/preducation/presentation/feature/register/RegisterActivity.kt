package com.kelompoksatuandsatu.preducation.presentation.feature.register

import android.annotation.SuppressLint
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
import com.kelompoksatuandsatu.preducation.databinding.ActivityRegisterBinding
import com.kelompoksatuandsatu.preducation.presentation.feature.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private lateinit var passwordInput: EditText
    private lateinit var showHideButtonPassword: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        val loginTextView = findViewById<TextView>(R.id.loginText)
        val loginTextView = binding.loginText
        val loginString = " Login Here"
        val loginSpannable = SpannableString(loginString)
        loginSpannable.setSpan(
            UnderlineSpan(),
            0,
            loginString.length,
            0
        )
        loginTextView.text = loginSpannable

        // Show & Hide Password
        passwordInput = findViewById(R.id.passwordInput)
        showHideButtonPassword = findViewById(R.id.showHidePasswordButton)

        showHideButtonPassword.setOnClickListener {
            togglePasswordVisibility()
        }

        // Show Message Box
        val registerButton = findViewById<ConstraintLayout>(R.id.cl_button_sign_up)
        registerButton.setOnClickListener {
            showErrorMessageBox()
        }

        loginTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
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
