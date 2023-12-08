package com.kelompoksatuandsatu.preducation.data.network.api.model.common

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?
)
