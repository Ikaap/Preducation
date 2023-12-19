package com.kelompoksatuandsatu.preducation.data.network.api.model.auth.register

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RegisterResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean
)
