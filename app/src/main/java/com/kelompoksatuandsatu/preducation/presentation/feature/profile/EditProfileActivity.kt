package com.kelompoksatuandsatu.preducation.presentation.feature.profile

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputLayout
import com.kelompoksatuandsatu.preducation.R

class EditProfileActivity : AppCompatActivity() {

    private lateinit var etLongName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etCountry: EditText
    private lateinit var etCity: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // Initialize EditText components
        etLongName = findViewById(R.id.et_long_name)
        etEmail = findViewById(R.id.et_email)
        etPhoneNumber = findViewById(R.id.et_phone_number)
        etCountry = findViewById(R.id.et_country)
        etCity = findViewById(R.id.et_city)

        // Initialize TextInputLayout components
        val tilName = findViewById<TextInputLayout>(R.id.tilName)
        val tilEmail = findViewById<TextInputLayout>(R.id.tilEmail)
        val tilPhoneNumber = findViewById<TextInputLayout>(R.id.tilPhoneNumber)
        val tilCountry = findViewById<TextInputLayout>(R.id.tilCountry)
        val tilCity = findViewById<TextInputLayout>(R.id.tilCity)

        // Set hint for each TextInputLayout
        tilName.hint = "Long Name"
        tilEmail.hint = "Email"
        tilPhoneNumber.hint = "Phone Number"
        tilCountry.hint = "Country"
        tilCity.hint = "City"

        // Initialize the "Update" button
        val btnUpdate = findViewById<ConstraintLayout>(R.id.cl_button_payment)
        btnUpdate.setOnClickListener {
            // Handle the button click event
            updateProfile()
        }
    }

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
}
