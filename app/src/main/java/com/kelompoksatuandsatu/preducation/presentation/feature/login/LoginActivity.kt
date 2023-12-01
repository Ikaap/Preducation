package com.kelompoksatuandsatu.preducation.presentation.feature.login

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

class LoginActivity : AppCompatActivity() {
    private lateinit var passwordInput: EditText
    private lateinit var showHideButtonPassword: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val registerTextView = findViewById<TextView>(R.id.registerText)
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
        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
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
