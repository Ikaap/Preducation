package com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.verifyotp

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OtpRequest(
    @SerializedName("otp")
    val otp: String?
)
