package com.kelompoksatuandsatu.preducation.model

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: LoginData
)

data class LoginData(
    val accessToken: String
)

data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val status: String,
    val data: RegisterData
)

data class RegisterData(
    val _id: String,
    val name: String,
    val phone: String,
    val role: String
)

data class OtpData(
    val email: String
)
