package com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoryclass

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CategoriesClassResponse(
    @SerializedName("data")
    val data: List<Data>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)
