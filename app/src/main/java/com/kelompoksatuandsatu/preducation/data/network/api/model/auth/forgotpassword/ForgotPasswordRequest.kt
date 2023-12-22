package com.kelompoksatuandsatu.preducation.data.network.api.model.auth.forgotpassword

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ForgotPasswordRequest(
    @SerializedName("email")
    val email: String?
)
