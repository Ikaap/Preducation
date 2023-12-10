package com.kelompoksatuandsatu.preducation.presentation.feature.register

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.method.PasswordTransformationMethod
import android.text.style.UnderlineSpan
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kelompoksatuandsatu.preducation.databinding.ActivityRegisterBinding
import com.kelompoksatuandsatu.preducation.model.Register
import com.kelompoksatuandsatu.preducation.model.RegisterResponse
import com.kelompoksatuandsatu.preducation.network.AuthService
import com.kelompoksatuandsatu.preducation.presentation.feature.login.LoginActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.otp.OtpActivity
import org.json.JSONObject
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }
    private lateinit var registerModel: Register

    private val authService: AuthService by inject()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        registerModel = Register(
            registerTitle = "Getting Started.!",
            nameLabel = "Name",
            emailLabel = "Email",
            phoneLabel = "Phone Number",
            passwordLabel = "Create a Password",
            buttonLabel = "Sign Up",
            loginTitle = "Already have an account?",
            loginText = "Login Here"
        )

        binding.lifecycleOwner = this
        binding.registerModel = registerModel

        val loginTextView = binding.loginText
        val loginString = registerModel.loginText
        val loginSpannable = SpannableString(loginString)
        loginSpannable.setSpan(
            UnderlineSpan(),
            0,
            loginString.length,
            0
        )
        loginTextView.text = loginSpannable

        // Show & Hide Password
        binding.showHidePasswordButton.setOnClickListener {
            togglePasswordVisibility()
        }

        // Show Message Box
        binding.signUpButton.setOnClickListener {
            val name = binding.nameInput.text.toString()
            val email = binding.emailInput.text.toString()
            val phone = binding.phoneNumberInput.text.toString()
            val password = binding.passwordInput.text.toString()

            if (isValidRegisterInput(name, email, phone, password)) {
                if (isValidEmail(email)) {
                    register(name, email, phone, password)
                } else {
                    Toast.makeText(this, "Invalid email format!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "All input fields is required", Toast.LENGTH_SHORT).show()
            }
        }

        loginTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun togglePasswordVisibility() {
        if (binding.passwordInput.transformationMethod == PasswordTransformationMethod.getInstance()) {
            binding.passwordInput.transformationMethod = null
        } else {
            binding.passwordInput.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }

    private fun isValidRegisterInput(email: String, name: String, phone: String, password: String): Boolean {
        return email.isNotEmpty() && name.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty()
    }

    private fun register(email: String, name: String, phone: String, password: String) {
        val call = authService.register(name, email, phone, password)

        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                val context = this@RegisterActivity

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.success == true) {
                        Toast.makeText(context, "Successfully register!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, OtpActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(context, "Sorry, there was an error!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    var errorBody = response.errorBody()?.string()
                    val jsonObject = JSONObject(errorBody)
                    val message = jsonObject.getString("message")
                    Toast.makeText(context, "Failed to register! $message", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to register! ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
