package com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Category(
    @SerializedName("_id")
    val id: String?,
    @SerializedName("name")
    val name: String?
)
