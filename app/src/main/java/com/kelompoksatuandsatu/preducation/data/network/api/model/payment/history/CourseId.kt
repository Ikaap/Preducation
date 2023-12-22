package com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class CourseId(
    @SerializedName("about")
    val about: String?,
    @SerializedName("category")
    val category: Category?,
    @SerializedName("classCode")
    val classCode: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("isActive")
    val isActive: Boolean?,
    @SerializedName("level")
    val level: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("targetAudience")
    val targetAudience: List<Any?>?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("totalDuration")
    val totalDuration: Int?,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("totalModule")
    val totalModule: Int?,
    @SerializedName("typeClass")
    val typeClass: String?
)
