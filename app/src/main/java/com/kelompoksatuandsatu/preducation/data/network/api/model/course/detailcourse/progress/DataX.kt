package com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.progress

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DataX(
    @SerializedName("courseId")
    val courseId: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("indexProgress")
    val indexProgress: Int?,
    @SerializedName("isActive")
    val isActive: Boolean?,
    @SerializedName("percentage")
    val percentage: Int?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("userId")
    val userId: String?,
    @SerializedName("__v")
    val v: Int?
)
