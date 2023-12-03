package com.kelompoksatuandsatu.preducation.presentation.feature.resetpassword

import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.ActivityResetPasswordBinding
import io.github.muddz.styleabletoast.StyleableToast

class ResetPasswordActivity : AppCompatActivity() {

    private val binding: ActivityResetPasswordBinding by lazy {
        ActivityResetPasswordBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val resetPasswordTextView = findViewById<TextView>(R.id.tv_send_email)
        val emailEditText = findViewById<EditText>(R.id.et_email)
        resetPasswordTextView.setOnClickListener {
            val email = emailEditText.text.toString()
            validateEmail(email)
        }
    }

    private fun validateEmail(email: String): Boolean {
        val tilEmail = binding.tilEmail
        val emailEditText = binding.etEmail

        return if (email.isEmpty()) {
            tilEmail.isErrorEnabled = true
            tilEmail.error = getString(R.string.text_error_email_empty)
            emailEditText.setBackgroundResource(R.drawable.bg_edit_text_error)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.isErrorEnabled = true
            tilEmail.error = getString(R.string.text_error_email_invalid)
            emailEditText.setBackgroundResource(R.drawable.bg_edit_text_error)
            false
        } else {
            tilEmail.isErrorEnabled = false
            emailEditText.setBackgroundResource(R.drawable.bg_edit_text_secondary_transparent)
            StyleableToast.makeText(
                this,
                getString(R.string.reset_password_successful),
                R.style.successtoast
            ).show()
            true
        }
    }
}
