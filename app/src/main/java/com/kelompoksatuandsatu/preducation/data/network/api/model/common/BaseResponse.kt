package com.kelompoksatuandsatu.preducation.data.network.api.model.common

import com.google.gson.annotations.SerializedName

data class BaseResponse(

    @SerializedName("message")
    val message: String?,

    @SerializedName("status")
    val status: String?,

    @SerializedName("success")
    val success: Boolean?
)
