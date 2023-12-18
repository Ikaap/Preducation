package com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OtpResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)