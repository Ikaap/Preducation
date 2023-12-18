package com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.verifyotp

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OtpResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("success")
    val success: Boolean?
)
