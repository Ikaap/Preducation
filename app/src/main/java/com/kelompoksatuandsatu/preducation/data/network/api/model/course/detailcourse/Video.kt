package com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Video(
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("index")
    val index: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("videoUrl")
    val videoUrl: String?
)
