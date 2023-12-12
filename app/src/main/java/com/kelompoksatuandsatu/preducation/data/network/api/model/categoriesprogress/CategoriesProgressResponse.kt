package com.kelompoksatuandsatu.preducation.data.network.api.model.categoriesprogress

import com.google.gson.annotations.SerializedName

data class CategoriesProgressResponse(
    @SerializedName("data")
    val data: List<CategoriesProgressItemResponse>?,

    @SerializedName("success")
    val success: Boolean?,

    @SerializedName("message")
    val message: String?
)