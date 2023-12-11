package com.kelompoksatuandsatu.preducation.data.network.api.model

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @SerializedName("password")
    val password: String,
    @SerializedName("confirmPassword")
    val confirmPassword: String
)
