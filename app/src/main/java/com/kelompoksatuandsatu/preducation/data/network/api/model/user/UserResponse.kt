package com.kelompoksatuandsatu.preducation.data.network.api.model.user

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<User>?,
    @SerializedName("status")
    val status: String
)
