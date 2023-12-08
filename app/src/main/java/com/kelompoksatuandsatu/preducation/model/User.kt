package com.kelompoksatuandsatu.preducation.model

data class User(
    val id: String,
    val email: String,
    val phone: String,
    val name: String,
    val username: String,
    val imageProfile: String,
    val country: String,
    val city: String,
    val role: String,
    val isVerify: Boolean,
    val createdAt: String
)
