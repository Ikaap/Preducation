package com.kelompoksatuandsatu.preducation.data.network.api.model.auth.forgotpassword

import com.google.gson.annotations.SerializedName
import com.kelompoksatuandsatu.preducation.model.auth.ForgotPasswordResponse

data class ForgotPasswordResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?
)

fun ForgotPasswordResponse.toForgotPassword() = ForgotPasswordResponse(
    success = this.success,
    status = this.status.orEmpty(),
    message = this.message.orEmpty()
)
