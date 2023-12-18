package com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.postemail

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class EmailOtpResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)
