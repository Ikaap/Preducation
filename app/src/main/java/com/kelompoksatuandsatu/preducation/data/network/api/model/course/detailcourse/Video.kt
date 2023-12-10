package com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.kelompoksatuandsatu.preducation.model.detailcourse.VideoViewParam

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

fun Video.toVideo() = VideoViewParam(
    duration = this.duration ?: 0,
    id = this.id.orEmpty(),
    index = this.index ?: 0,
    title = this.title.orEmpty(),
    videoUrl = this.videoUrl.orEmpty()
)

fun Collection<Video>.toVideoList() = this.map { it.toVideo() }
