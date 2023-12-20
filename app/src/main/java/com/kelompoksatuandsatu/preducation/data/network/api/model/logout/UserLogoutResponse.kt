package com.kelompoksatuandsatu.preducation.data.network.api.model.logout

import com.google.gson.annotations.SerializedName

class UserLogoutResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)
