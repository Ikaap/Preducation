package com.kelompoksatuandsatu.preducation.presentation.feature.editprofile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.ActivityEditProfileBinding
import com.kelompoksatuandsatu.preducation.utils.AssetWrapper
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import io.github.muddz.styleabletoast.StyleableToast
import org.koin.android.ext.android.inject

class EditProfileActivity : AppCompatActivity() {

    private val binding: ActivityEditProfileBinding by lazy {
        ActivityEditProfileBinding.inflate(layoutInflater)
    }

    private val viewModel: EditProfileViewModel by viewModels()

    private val assetWrapper: AssetWrapper by inject()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        etLongName = findViewById(R.id.et_long_name)
        etEmail = findViewById(R.id.et_email)
        etPhoneNumber = findViewById(R.id.et_phone_number)
        etCountry = findViewById(R.id.et_country)
        etCity = findViewById(R.id.et_city)
        ivBack = findViewById(R.id.iv_back)

        // Initialize TextInputLayout components
        val tilName = findViewById<TextInputLayout>(R.id.tilName)
        val tilEmail = findViewById<TextInputLayout>(R.id.tilEmail)
        val tilPhoneNumber = findViewById<TextInputLayout>(R.id.tilPhoneNumber)
        val tilCountry = findViewById<TextInputLayout>(R.id.tilCountry)
        val tilCity = findViewById<TextInputLayout>(R.id.tilCity)

        val btnUpdate = findViewById<ConstraintLayout>(R.id.cl_button_payment)
        btnUpdate.setOnClickListener {
            updateProfile()
        }
    }

    private fun setupForm() {
        binding.tilName.isVisible = true
        binding.tilEmail.isVisible = true
        binding.tilEmail.isEnabled = false
        binding.tilPhoneNumber.isVisible = true
        binding.tilPhoneNumber.isEnabled = false
    }

    private suspend fun showUserData() {
        val user = viewModel.getUserById()

        if (user != null) {
            binding.etLongName.setText(user.name())
            binding.etEmail.setText(user.email)
            binding.etPhoneNumber.setText(user.phoneNumber ?: "")
            binding.etCountry.setText(user.country ?: "")
            binding.etCity.setText(user.city ?: "")
        }
    }

    private fun setClickListeners() {
        binding.clButtonChange.setOnClickListener {
            if (validateLongName(longName)) {
                changeProfileData()
                finish()
                Toast.makeText(
                    this,
                    assetWrapper.getString(R.string.text_profile_update_success),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.clButtonChange.setOnClickListener {
            requestChangePassword()
        }
    }

    private fun changeProfileData() {
        val name = binding.etLongName.text.toString().trim()
        viewModel.changeProfile(name)
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
                getString(R.string.edit_profile_successful),
                R.style.successtoast
            ).show()
            true
        }
    }

    private fun validateLongName(longName: String): Boolean {
        val tilLongName = binding.tilName
        val longNameEditText = binding.etLongName

        return if (longName.isEmpty()) {
            tilLongName.isErrorEnabled = true
            tilLongName.error = getString(R.string.text_error_long_name_empty)
            longNameEditText.setBackgroundResource(R.drawable.bg_edit_text_error)
            false
        } else {
            tilLongName.isErrorEnabled = false
            longNameEditText.setBackgroundResource(R.drawable.bg_edit_text_secondary_transparent)
            StyleableToast.makeText(
                this,
                getString(R.string.edit_profile_successful),
                R.style.successtoast
            ).show()
            true
        }
    }

    private fun validatePhoneNumber(phoneNumber: String): Boolean {
        val tilPhoneNumber = binding.tilPhoneNumber
        val phoneNumberEditText = binding.etPhoneNumber

        return if (phoneNumber.isEmpty()) {
            tilPhoneNumber.isErrorEnabled = true
            tilPhoneNumber.error = getString(R.string.text_error_phone_number_empty)
            phoneNumberEditText.setBackgroundResource(R.drawable.bg_edit_text_error)
            false
        } else {
            tilPhoneNumber.isErrorEnabled = false
            phoneNumberEditText.setBackgroundResource(R.drawable.bg_edit_text_secondary_transparent)
            StyleableToast.makeText(
                this,
                getString(R.string.edit_profile_successful),
                R.style.successtoast
            ).show()
            true
        }
    }

    private fun validateCountry(country: String): Boolean {
        val tilCountry = binding.tilCountry
        val countryEditText = binding.etCountry

        return if (country.isEmpty()) {
            tilCountry.isErrorEnabled = true
            tilCountry.error = getString(R.string.text_error_country_empty)
            countryEditText.setBackgroundResource(R.drawable.bg_edit_text_error)
            false
        } else {
            tilCountry.isErrorEnabled = false
            countryEditText.setBackgroundResource(R.drawable.bg_edit_text_secondary_transparent)
            StyleableToast.makeText(
                this,
                getString(R.string.edit_profile_successful),
                R.style.successtoast
            ).show()
            true
        }
    }

    private fun validateCity(city: String): Boolean {
        val tilCity = binding.tilCity
        val cityEditText = binding.etCity

        return if (city.isEmpty()) {
            tilCity.isErrorEnabled = true
            tilCity.error = getString(R.string.text_error_city_empty)
            cityEditText.setBackgroundResource(R.drawable.bg_edit_text_error)
            false
        } else {
            tilCity.isErrorEnabled = false
            cityEditText.setBackgroundResource(R.drawable.bg_edit_text_secondary_transparent)
            StyleableToast.makeText(
                this,
                getString(R.string.edit_profile_successful),
                R.style.successtoast
            ).show()
            true
        }
    }

    private lateinit var etLongName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etCountry: EditText
    private lateinit var etCity: EditText
    private lateinit var ivBack: ImageView

    private fun updateProfile() {
        // Implement your logic for updating the profile here

        // For example, you can retrieve values from EditText fields
        val longName = etLongName.text.toString()
        val email = etEmail.text.toString()
        val phoneNumber = etPhoneNumber.text.toString()
        val country = etCountry.text.toString()
        val city = etCity.text.toString()

        // Perform the update using these values
        // Here, you can add your code to update the user's profile using the retrieved values
    }

    private fun observeData() {
        viewModel.changeProfileResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.clButtonChange.isVisible = true
                    binding.clButtonChange.isEnabled = true
                    showUserData()
                    showSuccessMessage()
                },
                doOnLoading = {
                    binding.clButtonChange.isVisible = false
                },
                doOnError = {
                    binding.clButtonChange.isVisible = true
                    binding.clButtonChange.isEnabled = true
                    Toast.makeText(
                        this,
                        assetWrapper.getString(R.string.text_change_profile_failed) + it.exception?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    private fun showSuccessMessage() {
        Toast.makeText(
            this,
            assetWrapper.getString(R.string.text_profile_update_success),
            Toast.LENGTH_SHORT
        ).show()
    }
}
