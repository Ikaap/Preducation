package com.kelompoksatuandsatu.preducation.data.network.api.model

import com.google.gson.annotations.SerializedName

data class ResetPasswordResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: Data?
)
