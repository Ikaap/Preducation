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

fun OtpResponse.toOtpResponse() = com.kelompoksatuandsatu.preducation.model.auth.otp.verifyotp.OtpResponse(
    message = this.message.orEmpty(),
    status = this.status.orEmpty(),
    success = this.success ?: false
)
