package com.kelompoksatuandsatu.preducation.presentation.feature.editprofile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.textfield.TextInputLayout
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.ActivityEditProfileBinding
import com.kelompoksatuandsatu.preducation.utils.exceptions.ApiException
import com.kelompoksatuandsatu.preducation.utils.exceptions.NoInternetException
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import io.github.muddz.styleabletoast.StyleableToast
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class EditProfileActivity : AppCompatActivity() {

    private val binding: ActivityEditProfileBinding by lazy {
        ActivityEditProfileBinding.inflate(layoutInflater)
    }

    private val viewModel: EditProfileViewModel by viewModel()

    private var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getData()
        setDataProfile()
        setForm()
        setOnClickListener()
    }

    private fun getData() {
        viewModel.getUserById()
    }

    private fun setDataProfile() {
        viewModel.getProfile.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.root.isVisible = true
                    binding.ivAddPhotoUser.isVisible = true
                    binding.clButtonChange.isVisible = true

                    it.payload?.let {
                        binding.etLongName.setText(it.name.orEmpty())
                        binding.etEmail.setText(it.email.orEmpty())
                        binding.etPhoneNumber.setText(it.phone.orEmpty())
                        binding.etCountry.setText(it.country.orEmpty())
                        binding.etCity.setText(it.city.orEmpty())
                        binding.ivUserPhoto.load(it.imageProfile.orEmpty())

                        email = it.email.toString()
                    }
                },
                doOnLoading = {
                    binding.root.isVisible = true
                    binding.ivAddPhotoUser.isVisible = false
                },
                doOnError = {
                    binding.root.isVisible = true
                    binding.ivAddPhotoUser.isVisible = false
                }
            )
        }
    }

    private fun setForm() {
        binding.ivUserPhoto.isVisible = true
        binding.ivAddPhotoUser.isVisible = true
        binding.tilName.isVisible = true
        binding.tilEmail.isVisible = true
        binding.tilEmail.isEnabled = false
        binding.tilPhoneNumber.isVisible = true
        binding.tilCountry.isVisible = true
        binding.tilCity.isVisible = true
        binding.clButtonChange.isVisible = true
    }

    private fun observeData() {
        viewModel.updateProfileResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.root.isVisible = true
                    binding.ivAddPhotoUser.isVisible = true
                    binding.clButtonChange.isVisible = true

                    it.payload?.let {
                        binding.etLongName.setText(it.name.orEmpty())
                        binding.etEmail.setText(it.email.orEmpty())
                        binding.etPhoneNumber.setText(it.phone.orEmpty())
                        binding.etCountry.setText(it.country.orEmpty())
                        binding.etCity.setText(it.city.orEmpty())
                        binding.ivUserPhoto.load(it.imageProfile.orEmpty())
                    }
                },
                doOnLoading = {
                    binding.root.isVisible = true
                    binding.ivAddPhotoUser.isVisible = false
                },
                doOnError = {
                    binding.root.isVisible = true
                    binding.ivAddPhotoUser.isVisible = false

                    if (it.exception is ApiException) {
                        if (it.exception.getParsedErrorProfile()?.success == false) {
                            if (it.exception.httpCode == 500) {
                                binding.layoutCommonState.clServerError.isGone = false
                                binding.layoutCommonState.ivServerError.isGone = false
                                StyleableToast.makeText(
                                    this,
                                    "SERVER ERROR",
                                    R.style.failedtoast
                                ).show()
                            } else if (it.exception.getParsedErrorProfile()?.success == false) {
                                binding.layoutCommonState.tvError.text =
                                    it.exception.getParsedErrorProfile()?.message
                                StyleableToast.makeText(
                                    this,
                                    it.exception.getParsedErrorProfile()?.message,
                                    R.style.failedtoast
                                ).show()
                            }
                        }
                    } else if (it.exception is NoInternetException) {
                        if (!it.exception.isNetworkAvailable(this)) {
                            binding.layoutCommonState.clNoConnection.isGone = false
                            binding.layoutCommonState.ivNoConnection.isGone = false
                            StyleableToast.makeText(
                                this,
                                "tidak ada internet",
                                R.style.failedtoast
                            ).show()
                        }
                    }
                }
            )
        }
    }

    private fun setOnClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.clButtonChange.setOnClickListener {
            if (isFormValid()) {
                changeProfileData()
                StyleableToast.makeText(
                    this,
                    "Update Profile Successfully",
                    R.style.successtoast
                ).show()
                observeData()
            } else {
                showToast(R.string.text_error_form_not_valid)
            }
        }

        binding.ivAddPhotoUser.setOnClickListener {
            imagePicker()
        }
    }

    private var getFile: File? = null

    private fun imagePicker() {
        ImagePicker.with(this)
            .cropSquare()
            .galleryOnly()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

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

    private fun changeProfileData() {
        val imageFile = getFile
        if (imageFile != null) {
            val imageRequestBody =
                imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "imageProfile",
                imageFile.name,
                imageRequestBody
            )
            // Logika pengiriman data profil
            val name = binding.etLongName.text.toString().trim()
            val phone = binding.etPhoneNumber.text.toString().trim()
            val country = binding.etCountry.text.toString().trim()
            val city = binding.etCity.text.toString().trim()

            viewModel.updateProfile(
                name.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                email.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                phone.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                country.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                city.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                imageMultipart
            )
        }
    }

    private fun isFormValid(): Boolean {
        val name = binding.etLongName.text.toString().trim()
        val country = binding.etCountry.text.toString().trim()
        val city = binding.etCity.text.toString().trim()
        val phoneNumber = binding.etPhoneNumber.text.toString().trim()

        return checkNameValidation(name) &&
                checkPhoneNumberValidation(phoneNumber) &&
                checkCountryValidation(country) &&
                checkCityValidation(city)
    }

    private fun checkNameValidation(name: String): Boolean {
        val tilLongName = binding.tilName
        val longNameEditText = binding.etLongName

        return when {
            name.isEmpty() -> {
                showError(tilLongName, longNameEditText, R.string.text_error_long_name_empty)
                false
            }

            !Regex("^[A-Za-z]+(?:\\s[A-Za-z]+)*\$").matches(name) -> {
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
