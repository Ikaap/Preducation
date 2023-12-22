package com.kelompoksatuandsatu.preducation.presentation.feature.changepassword

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputLayout
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.data.network.api.model.changepassword.ChangePasswordRequest
import com.kelompoksatuandsatu.preducation.databinding.ActivityChangePasswordBinding
import com.kelompoksatuandsatu.preducation.utils.AssetWrapper
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import io.github.muddz.styleabletoast.StyleableToast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePasswordActivity : AppCompatActivity() {

    private val binding: ActivityChangePasswordBinding by lazy {
        ActivityChangePasswordBinding.inflate(layoutInflater)
    }
    private val viewModel: ChangePasswordViewModel by viewModel()

    private val MIN_PASSWORD_LENGTH = 8

    private val assetWrapper: AssetWrapper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setOnClickListener()
        setForm()
    }

    private fun setOnClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.clButtonContinue.setOnClickListener {
            if (isFormValid()) {
                setUpdatePassword()
                StyleableToast.makeText(
                    this,
                    "Change Password Successfully",
                    R.style.successtoast
                ).show()
                navigateToProfile()
            } else {
                showErrorToast(R.string.text_error_form_not_valid)
            }
        }
    }

    private fun navigateToProfile() {
        onBackPressed()
    }

    private fun setUpdatePassword() {
        updatePassword()
        viewModel.updatedPassword.observe(this) {
            it.proceedWhen(
                doOnError = {
                    binding.root.isVisible = true
                    if (it.exception is ApiException) {
                        showErrorToast(R.string.text_error_update_password)
                    }
                },
                doOnLoading = {
                    binding.root.isVisible = true
                },
                doOnSuccess = {
                    binding.root.isVisible = true
                    binding.etOldPassword.text?.clear()
                    binding.etNewPassword.text?.clear()
                    binding.etConfirmPassword.text?.clear()
                }
            )
        }
    }

    private fun updatePassword() {
        val newPassword = binding.etNewPassword.text.toString().trim()
        val oldPassword = binding.etOldPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        viewModel.updatePassword(
            ChangePasswordRequest(
                newPassword = newPassword,
                oldPassword = oldPassword,
                confirmPassword = confirmPassword
            )
        )
    }

    private fun setForm() {
        binding.tilOld.isVisible = true
        binding.tilNew.isVisible = true
        binding.tilConfirm.isVisible = true
    }

    private fun isFormValid(): Boolean {
        val oldPassword = binding.etOldPassword.text.toString().trim()
        val newPassword = binding.etNewPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        return checkOldPasswordValidation(oldPassword, newPassword) &&
            checkNewPasswordValidation(newPassword) &&
            checkConfirmPasswordValidation(newPassword, confirmPassword)
    }

    private fun checkOldPasswordValidation(oldPassword: String, newPassword: String): Boolean {
        val tilOldPassword = binding.tilOld
        val oldPasswordEditText = binding.etOldPassword

        if (oldPassword.isEmpty()) {
            showError(tilOldPassword, oldPasswordEditText, R.string.text_error_password_empty)
            return false
        }

        if (oldPassword.length < MIN_PASSWORD_LENGTH) {
            showError(tilOldPassword, oldPasswordEditText, R.string.text_error_password_invalid)
            return false
        }

        if (oldPassword == newPassword) {
            showError(
                tilOldPassword,
                oldPasswordEditText,
                R.string.text_error_old_new_password_match
            )
            return false
        }

        clearError(tilOldPassword, oldPasswordEditText)
        return true
    }

    private fun checkNewPasswordValidation(newPassword: String): Boolean {
        val tilNewPassword = binding.tilNew
        val newPasswordEditText = binding.etNewPassword

        if (newPassword.isEmpty()) {
            showError(tilNewPassword, newPasswordEditText, R.string.text_error_password_empty)
            return false
        }

        if (newPassword.length < MIN_PASSWORD_LENGTH) {
            showError(tilNewPassword, newPasswordEditText, R.string.text_error_password_invalid)
            return false
        }

        clearError(tilNewPassword, newPasswordEditText)
        return true
    }

    private fun checkConfirmPasswordValidation(
        newPassword: String,
        confirmPassword: String
    ): Boolean {
        val tilConfirmPassword = binding.tilConfirm
        val confirmPasswordEditText = binding.etConfirmPassword

        if (confirmPassword.isEmpty()) {
            showError(
                tilConfirmPassword,
                confirmPasswordEditText,
                R.string.text_error_password_empty
            )
            return false
        }

        if (confirmPassword.length < MIN_PASSWORD_LENGTH) {
            showError(
                tilConfirmPassword,
                confirmPasswordEditText,
                R.string.text_error_password_invalid
            )
            return false
        }

        if (confirmPassword != newPassword) {
            showError(
                tilConfirmPassword,
                confirmPasswordEditText,
                R.string.text_error_confirm_password_mismatch
            )
            return false
        }

        clearError(tilConfirmPassword, confirmPasswordEditText)
        return true
    }

    private fun showError(til: TextInputLayout, editText: EditText, errorMessage: Int) {
        til.isErrorEnabled = true
        til.error = getString(errorMessage)
        editText.setBackgroundResource(R.drawable.bg_edit_text_error)
    }

    private fun clearError(til: TextInputLayout, editText: EditText) {
        til.isErrorEnabled = false
        editText.setBackgroundResource(R.drawable.bg_edit_text_secondary_transparent)
    }

    private fun showErrorToast(messageResId: Int) {
        showToast(getString(messageResId))
    }

    private fun showToast(message: String) {
        StyleableToast.makeText(this, message, R.style.successtoast).show()
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, ChangePasswordActivity::class.java)
            context.startActivity(intent)
        }
    }
}
