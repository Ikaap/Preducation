package com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.kelompoksatuandsatu.preducation.model.course.detailcourse.VideoViewParam

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
    val videoUrl: String?,
    @SerializedName("isWatch")
    val isWatch: Boolean?,
    @SerializedName("nextVideo")
    val nextVideo: Boolean?

)

fun Video.toVideo() = VideoViewParam(
    duration = this.duration ?: 0,
    id = this.id.orEmpty(),
    index = this.index ?: 0,
    title = this.title.orEmpty(),
    videoUrl = this.videoUrl.orEmpty(),
    isWatch = this.isWatch ?: false,
    nextVideo = this.nextVideo ?: false
)

fun Collection<Video>.toVideoList() = this.map { it.toVideo() }
