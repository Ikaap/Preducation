package com.kelompoksatuandsatu.preducation.presentation.feature.resetpassword

import android.os.Bundle
import android.util.Patterns
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

        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.tvSendEmail.setOnClickListener {
            val email = binding.etEmail.text.toString()
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
