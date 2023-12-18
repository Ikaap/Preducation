package com.kelompoksatuandsatu.preducation.data.network.api.model.changepassword

import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: List<ChangePasswordItem>?
)
