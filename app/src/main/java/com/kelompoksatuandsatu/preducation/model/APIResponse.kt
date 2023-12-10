package com.kelompoksatuandsatu.preducation.model

data class APIResponse(
    val success: Boolean,
    val message: String,
    val data: UserData
)

data class UserData(
    val accessToken: String
)
