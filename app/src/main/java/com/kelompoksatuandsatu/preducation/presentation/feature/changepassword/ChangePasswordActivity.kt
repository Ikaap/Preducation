package com.kelompoksatuandsatu.preducation.presentation.feature.changepassword

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.commit
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.ActivityChangePasswordBinding
import com.kelompoksatuandsatu.preducation.presentation.feature.profile.ProfileFragment
import com.kelompoksatuandsatu.preducation.utils.AssetWrapper
import io.github.muddz.styleabletoast.StyleableToast
import org.koin.android.ext.android.inject

class ChangePasswordActivity : AppCompatActivity() {

    private val binding: ActivityChangePasswordBinding by lazy {
        ActivityChangePasswordBinding.inflate(layoutInflater)
    }

//    private val viewModel: ChangePasswordViewModel by viewModels()

    private val MIN_PASSWORD_LENGTH = 8

    private val assetWrapper: AssetWrapper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val changePasswordTextView = findViewById<TextView>(R.id.tv_change_password)
        val oldPasswordEditText = findViewById<EditText>(R.id.et_old_password)
        val newPasswordEditText = findViewById<EditText>(R.id.et_new_password)
        val confirmPasswordEditText = findViewById<EditText>(R.id.et_confirm_password)
        val backButton = findViewById<ImageView>(R.id.iv_back)
        backButton.setOnClickListener {
            onBackPressed()
        }

        val continueButton = findViewById<ConstraintLayout>(R.id.cl_button_continue)
        continueButton.setOnClickListener {
            saveChangesAndNavigateNext()
        }
        changePasswordTextView.setOnClickListener {
            val oldPassword = oldPasswordEditText.text.toString()
            val newPassword = newPasswordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            validateOldPassword(oldPassword)
            validateNewPassword(newPassword)
            validateConfirmPassword(newPassword, confirmPassword)
        }
    }

    private fun saveChangesAndNavigateNext() {
        val profileFragment = ProfileFragment()
        supportFragmentManager.commit {
            replace(R.id.cl_button_continue, profileFragment)
        }
    }

    private fun validateOldPassword(newPassword: String): Boolean {
        val tilOldPassword = binding.tilOld
        val oldPasswordEditText = binding.etOldPassword

        return if (newPassword.isEmpty()) {
            tilOldPassword.isErrorEnabled = true
            tilOldPassword.error = assetWrapper.getString(R.string.text_error_password_empty)
            oldPasswordEditText.setBackgroundResource(R.drawable.bg_edit_text_error)
            false
        } else if (newPassword.length < MIN_PASSWORD_LENGTH) {
            tilOldPassword.isErrorEnabled = true
            tilOldPassword.error = assetWrapper.getString(R.string.text_error_password_length)
            oldPasswordEditText.setBackgroundResource(R.drawable.bg_edit_text_error)
            false
        } else {
            tilOldPassword.isErrorEnabled = false
            oldPasswordEditText.setBackgroundResource(R.drawable.bg_edit_text_secondary_transparent)
            StyleableToast.makeText(
                this,
                assetWrapper.getString(R.string.reset_password_successful),
                R.style.successtoast
            ).show()
            true
        }
    }

    private fun validateNewPassword(newPassword: String): Boolean {
        val tilNewPassword = binding.tilNew
        val newPasswordEditText = binding.etNewPassword

        return if (newPassword.isEmpty()) {
            tilNewPassword.isErrorEnabled = true
            tilNewPassword.error = assetWrapper.getString(R.string.text_error_password_empty)
            newPasswordEditText.setBackgroundResource(R.drawable.bg_edit_text_error)
            false
        } else if (newPassword.length < MIN_PASSWORD_LENGTH) {
            tilNewPassword.isErrorEnabled = true
            tilNewPassword.error = assetWrapper.getString(R.string.text_error_password_length)
            newPasswordEditText.setBackgroundResource(R.drawable.bg_edit_text_error)
            false
        } else {
            tilNewPassword.isErrorEnabled = false
            newPasswordEditText.setBackgroundResource(R.drawable.bg_edit_text_secondary_transparent)
            StyleableToast.makeText(
                this,
                assetWrapper.getString(R.string.reset_password_successful),
                R.style.successtoast
            ).show()
            true
        }
    }

    private fun validateConfirmPassword(confirmPassword: String, newPassword: String): Boolean {
        val tilConfirmPassword = binding.tilConfirm
        val confirmPasswordEditText = binding.etConfirmPassword

        return if (confirmPassword.isEmpty()) {
            tilConfirmPassword.isErrorEnabled = true
            tilConfirmPassword.error = assetWrapper.getString(R.string.text_error_password_empty)
            confirmPasswordEditText.setBackgroundResource(R.drawable.bg_edit_text_error)
            false
        } else if (confirmPassword.length < MIN_PASSWORD_LENGTH) {
            tilConfirmPassword.isErrorEnabled = true
            tilConfirmPassword.error = assetWrapper.getString(R.string.text_error_password_length)
            confirmPasswordEditText.setBackgroundResource(R.drawable.bg_edit_text_error)
            false
        } else if (confirmPassword != newPassword) {
            tilConfirmPassword.isErrorEnabled = true
            tilConfirmPassword.error =
                assetWrapper.getString(R.string.text_error_confirm_password_mismatch)
            confirmPasswordEditText.setBackgroundResource(R.drawable.bg_edit_text_error)
            false
        } else {
            tilConfirmPassword.isErrorEnabled = false
            confirmPasswordEditText.setBackgroundResource(R.drawable.bg_edit_text_secondary_transparent)
            StyleableToast.makeText(
                this,
                assetWrapper.getString(R.string.reset_password_successful),
                R.style.successtoast
            ).show()
            true
        }
    }
}
