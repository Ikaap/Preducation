package com.kelompoksatuandsatu.preducation.presentation.feature.profile

import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputLayout
import com.kelompoksatuandsatu.preducation.R

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var tilOld: TextInputLayout
    private lateinit var tilNew: TextInputLayout
    private lateinit var etOldPassword: EditText
    private lateinit var etNewPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnContinue: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        // Initialize views
        tilOld = findViewById(R.id.tilOld)
        tilNew = findViewById(R.id.tilNew)
        etOldPassword = findViewById(R.id.et_old_password)
        etNewPassword = findViewById(R.id.et_new_password)
        btnContinue = findViewById(R.id.cl_button_continue)

        btnContinue.setOnClickListener {
            handleChangePassword()
        }
    }

    private fun handleChangePassword() {
        // Reset errors
        tilOld.error = null
        tilNew.error = null

        // Get entered passwords
        val oldPassword = etOldPassword.text.toString().trim()
        val newPassword = etNewPassword.text.toString().trim()
        val confirmPassword = etConfirmPassword.text.toString().trim()

        // Validate old password
        if (TextUtils.isEmpty(oldPassword)) {
            tilOld.error = "Enter your old password"
            return
        }

        // TODO: Check if the old password is correct (You need to implement this logic)
        if (!isOldPasswordCorrect(oldPassword)) {
            tilOld.error = "Incorrect old password"
            return
        }

        // Validate new password
        if (TextUtils.isEmpty(newPassword)) {
            tilNew.error = "Enter your new password"
            return
        }

        // Check if new password meets criteria (e.g., minimum length)
        if (newPassword.length < 8) {
            tilNew.error = "Password must be at least 8 characters long"
            return
        }

        // Validate if new passwords match
        if (newPassword != confirmPassword) {
            tilNew.error = "Passwords do not match"
            return
        }

        // TODO: Implement your password change logic here
        // For example, you can call a method in your authentication manager
        // to change the password for the current user.

        // Show success message or handle errors accordingly
        Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show()
    }

    private fun isOldPasswordCorrect(oldPassword: String): Boolean {
        // TODO: Implement your logic to check if the old password is correct
        // For demonstration purposes, always return true
        return true
    }
}
