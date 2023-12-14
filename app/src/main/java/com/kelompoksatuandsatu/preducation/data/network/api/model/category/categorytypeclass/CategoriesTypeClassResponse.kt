package com.kelompoksatuandsatu.preducation.data.network.api.model.category.categorytypeclass

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CategoriesTypeClassResponse(
    @SerializedName("data")
    val data: List<Data>?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("message")
    val message: String?
)
