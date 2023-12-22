package com.kelompoksatuandsatu.preducation.data.network.api.model.auth.logout

import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String
)
