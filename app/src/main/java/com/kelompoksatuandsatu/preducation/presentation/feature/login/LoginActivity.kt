package com.kelompoksatuandsatu.preducation.presentation.feature.login

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.method.PasswordTransformationMethod
import android.text.style.UnderlineSpan
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kelompoksatuandsatu.preducation.databinding.ActivityLoginBinding
import com.kelompoksatuandsatu.preducation.model.Login
import com.kelompoksatuandsatu.preducation.model.LoginResponse
import com.kelompoksatuandsatu.preducation.network.AuthService
import com.kelompoksatuandsatu.preducation.presentation.feature.main.MainActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.register.RegisterActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.resetpassword.ResetPasswordActivity
import org.json.JSONObject
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private lateinit var loginModel: Login

    private val authService: AuthService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loginModel = Login(
            loginTitle = "Login",
            emailLabel = "Email",
            passwordLabel = "Password",
            forgotLabel = "Forgot Password",
            buttonLabel = "Sign In",
            registerTitle = "Don't have account yet?",
            registerText = "Register Here",
            withoutText = "Enter Without Logging In"
        )

        binding.lifecycleOwner = this
        binding.loginModel = loginModel

        val registerTextView = binding.registerText
        val registerString = loginModel.registerText
        val registerSpannable = SpannableString(registerString)
        registerSpannable.setSpan(
            UnderlineSpan(),
            0,
            registerString.length,
            0
        )
        registerTextView.text = registerSpannable

        // Show & Hide Password
        binding.showHidePasswordButton.setOnClickListener {
            togglePasswordVisibility()
        }

        // Show Message Box
        binding.signInButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()

            if (isValidLoginInput(email, password)) {
                if (isValidEmail(email)) {
                    login(email, password)
                } else {
                    Toast.makeText(this, "Invalid email format!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Email and Password is required!", Toast.LENGTH_SHORT).show()
            }
        }

        registerTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.forgotPasswordText.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }
        binding.tvWithoutLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
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

    private fun isValidLoginInput(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

    private fun login(email: String, password: String) {
        val call = authService.login(email, password)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val context = this@LoginActivity

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.success == true) {
                        Toast.makeText(context, "Successfully login!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(context, "Failed to login! Wrong Email or Password", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val jsonObject = JSONObject(errorBody)
                    val message = jsonObject.getString("message")
                    Toast.makeText(context, "Failed to login! $message", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to login! ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
