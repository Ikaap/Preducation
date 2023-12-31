package com.kelompoksatuandsatu.preducation.data.network.api.model.auth.forgotpassword

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?
)
