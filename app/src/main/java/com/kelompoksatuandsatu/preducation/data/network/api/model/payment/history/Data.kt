package com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("_id")
    val _id: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("level")
    val level: String?,
    @SerializedName("totalModule")
    val totalModule: Int?,
    @SerializedName("totalDuration")
    val totalDuration: Int?,
    @SerializedName("totalRating")
    val totalRating: Int?,
    @SerializedName("status")
    val status: String?
)
