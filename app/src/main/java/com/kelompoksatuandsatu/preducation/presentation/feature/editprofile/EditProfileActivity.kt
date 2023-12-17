package com.kelompoksatuandsatu.preducation.presentation.feature.editprofile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.core.view.isVisible
import coil.load
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.textfield.TextInputLayout
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.UserRequest
import com.kelompoksatuandsatu.preducation.databinding.ActivityEditProfileBinding
import com.kelompoksatuandsatu.preducation.utils.AssetWrapper
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import io.github.muddz.styleabletoast.StyleableToast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class EditProfileActivity : AppCompatActivity() {

    private val binding: ActivityEditProfileBinding by lazy {
        ActivityEditProfileBinding.inflate(layoutInflater)
    }

    private val viewModel: EditProfileViewModel by viewModel()

    private val assetWrapper: AssetWrapper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setOnClickListener()
        showUpdateProfile()
        setForm()
        setDataProfile()
        getData()
    }

    private fun setOnClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.clButtonChange.setOnClickListener {
            val userId = intent.getStringExtra("USER_ID")
            if (isFormValid()) {
                changeProfileData(userId.orEmpty())
            } else {
                showToast(R.string.text_error_form_not_valid)
            }
        }

        binding.ivAddPhotoUser.setOnClickListener {
            imagePicker()
        }
    }

    private fun setForm() {
        binding.ivUserPhoto.isVisible = true
        binding.ivAddPhotoUser.isVisible = true
        binding.tilName.isVisible = true
        binding.tilEmail.isVisible = true
        binding.tilPhoneNumber.isVisible = true
        binding.tilCountry.isVisible = true
        binding.tilCity.isVisible = true
        binding.clButtonChange.isVisible = true
    }

    private fun setDataProfile() {
        viewModel.getProfile.observe(this) {
            it.proceedWhen(
                doOnLoading = {
                    binding.root.isVisible = true
//                    binding.ivAddPhotoUser.isVisible = false
//                    binding.clButtonChange.isVisible = false
                },
                doOnError = {
                    binding.root.isVisible = true
//                    binding.ivAddPhotoUser.isVisible = false
//                    binding.clButtonChange.isVisible = false
                },
                doOnSuccess = {
                    binding.root.isVisible = true
                    binding.ivAddPhotoUser.isVisible = true
                    binding.clButtonChange.isVisible = true
//                    binding.etLongName.setText(it.payload?.name.orEmpty())
//                    binding.etEmail.setText(it.payload?.email.orEmpty())
//                    binding.etPhoneNumber.setText(it.payload?.phoneNumber.orEmpty())
//                    binding.etCountry.setText(it.payload?.country.orEmpty())
//                    binding.etCity.setText(it.payload?.city.orEmpty())
//                    binding.ivUserPhoto.load(it.payload?.imageUrl.orEmpty())
                }
            )
        }
    }

    private fun getData() {
        viewModel.getUserById()
    }

    private fun imagePicker() {
        ImagePicker.with(this)
            .cropSquare()
            .galleryOnly()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start()
    }

    private var getFile: File? = null

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )

        if (resultCode == Activity.RESULT_OK) {
            val uri: Uri? = data?.data
            val img = uri?.toFile()
            binding.ivUserPhoto.load(uri)
            getFile = img
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showUpdateProfile() {
        val idUser = intent.getStringExtra("USER_ID")
        idUser?.let {
            viewModel.getUserById()
        }
    }

    private fun changeProfileData(userId: String) {
        val name = binding.etLongName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhoneNumber.text.toString().trim()
        val country = binding.etCountry.text.toString().trim()
        val city = binding.etCity.text.toString().trim()

        val userRequest = UserRequest(
            email = email,
            phone = phone,
            name = name,
            imageProfile = null,
            country = country,
            city = city
        )

        viewModel.updateProfile(userId, userRequest)
    }

    private fun isFormValid(): Boolean {
        val email = binding.etEmail.text.toString().trim()
        val name = binding.etLongName.text.toString().trim()
        val country = binding.etCountry.text.toString().trim()
        val city = binding.etCity.text.toString().trim()
        val phoneNumber = binding.etPhoneNumber.text.toString().trim()

        return checkNameValidation(name) &&
            checkEmailValidation(email) &&
            checkPhoneNumberValidation(phoneNumber) &&
            checkCountryValidation(country) &&
            checkCityValidation(city)
    }

    private fun checkEmailValidation(email: String): Boolean {
        val tilEmail = binding.tilEmail
        val emailEditText = binding.etEmail

        return if (email.isEmpty()) {
            showError(tilEmail, emailEditText, R.string.text_error_email_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError(tilEmail, emailEditText, R.string.text_error_email_invalid)
            false
        } else {
            clearError(tilEmail, emailEditText)
            true
        }
    }

    private fun checkNameValidation(name: String): Boolean {
        val tilLongName = binding.tilName
        val longNameEditText = binding.etLongName

        return when {
            name.isEmpty() -> {
                showError(tilLongName, longNameEditText, R.string.text_error_long_name_empty)
                false
            }

            !Regex("^[A-Za-z]+$").matches(name) -> {
                showError(
                    tilLongName,
                    longNameEditText,
                    R.string.text_error_long_name_invalid_characters
                )
                false
            }

            else -> {
                clearError(tilLongName, longNameEditText)
                true
            }
        }
    }

    private fun checkCountryValidation(country: String): Boolean {
        val tilCountry = binding.tilCountry
        val countryEditText = binding.etCountry

        return when {
            country.isEmpty() -> {
                showError(tilCountry, countryEditText, R.string.text_error_country_empty)
                false
            }

            !Regex("^[A-Za-z]+$").matches(country) -> {
                showError(
                    tilCountry,
                    countryEditText,
                    R.string.text_error_country_invalid_characters
                )
                false
            }

            else -> {
                clearError(tilCountry, countryEditText)
                true
            }
        }
    }

    private fun checkCityValidation(city: String): Boolean {
        val tilCity = binding.tilCity
        val cityEditText = binding.etCity

        return when {
            city.isEmpty() -> {
                showError(tilCity, cityEditText, R.string.text_error_city_empty)
                false
            }

            !Regex("^[A-Za-z]+$").matches(city) -> {
                showError(tilCity, cityEditText, R.string.text_error_city_invalid_characters)
                false
            }

            else -> {
                clearError(tilCity, cityEditText)
                true
            }
        }
    }

    private fun checkPhoneNumberValidation(phoneNumber: String): Boolean {
        val tilPhoneNumber = binding.tilPhoneNumber
        val phoneNumberEditText = binding.etPhoneNumber

        return when {
            phoneNumber.isEmpty() -> {
                showError(
                    tilPhoneNumber,
                    phoneNumberEditText,
                    R.string.text_error_phone_number_empty
                )
                false
            }

            !Regex("^[0-9]+$").matches(phoneNumber) -> {
                showError(
                    tilPhoneNumber,
                    phoneNumberEditText,
                    R.string.text_error_phone_number_invalid_characters
                )
                false
            }

            else -> {
                clearError(tilPhoneNumber, phoneNumberEditText)
                true
            }
        }
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

    private fun showToast(messageResId: Int) {
        StyleableToast.makeText(this, getString(messageResId), R.style.successtoast).show()
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, EditProfileActivity::class.java)
            context.startActivity(intent)
        }
    }
}
