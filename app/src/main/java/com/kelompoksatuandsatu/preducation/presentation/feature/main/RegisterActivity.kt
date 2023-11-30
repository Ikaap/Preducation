package com.kelompoksatuandsatu.preducation.presentation.feature.main

import android.os.Bundle
import android.text.SpannableString
import android.text.method.PasswordTransformationMethod
import android.text.style.UnderlineSpan
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kelompoksatuandsatu.preducation.R

class RegisterActivity : AppCompatActivity() {
    private lateinit var passwordInput: EditText
    private lateinit var showHideButtonPassword: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val loginTextView = findViewById<TextView>(R.id.loginText)
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
        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            showErrorMessageBox()
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
