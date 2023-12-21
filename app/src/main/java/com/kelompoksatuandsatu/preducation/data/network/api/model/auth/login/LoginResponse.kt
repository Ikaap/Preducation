package com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.kelompoksatuandsatu.preducation.model.auth.UserLoginResponse

@Keep
data class LoginResponse(
    @SerializedName("data")
    val data: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)

fun LoginResponse.toLoginResponse() = UserLoginResponse(
    data = this.data,
    message = this.message,
    success = this.success
)
