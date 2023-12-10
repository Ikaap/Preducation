package com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginRequest(
    @SerializedName("identifier")
    val identifier: String?,
    @SerializedName("password")
    val password: String?
)
