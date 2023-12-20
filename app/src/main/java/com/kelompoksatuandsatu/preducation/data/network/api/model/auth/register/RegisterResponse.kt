package com.kelompoksatuandsatu.preducation.data.network.api.model.auth.register

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.kelompoksatuandsatu.preducation.model.auth.UserRegisterResponse

@Keep
data class RegisterResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean
)

fun RegisterResponse.toRegisterResponse() = UserRegisterResponse(
    data = this.data,
    message = this.message.orEmpty(),
    success = this.success
)
