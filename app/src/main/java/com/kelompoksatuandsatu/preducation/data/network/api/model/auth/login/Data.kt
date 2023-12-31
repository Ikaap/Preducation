package com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("_id")
    val id: String,
    @SerializedName("accessToken")
    val accessToken: String
)
