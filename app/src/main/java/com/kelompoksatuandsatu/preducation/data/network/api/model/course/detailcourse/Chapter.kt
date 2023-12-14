package com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.kelompoksatuandsatu.preducation.model.course.detailcourse.ChapterViewParam

@Keep
data class Chapter(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("isActive")
    val isActive: Boolean?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("totalDuration")
    val totalDuration: Int?,
    @SerializedName("videos")
    val videos: List<Video>?
)

fun Chapter.toChapter() = ChapterViewParam(
    id = this.id.orEmpty(),
    createdAt = this.createdAt.orEmpty(),
    isActive = this.isActive ?: false,
    title = this.title.orEmpty(),
    totalDuration = this.totalDuration ?: 0,
    videos = this.videos?.toVideoList()
)

fun Collection<Chapter>.toChapterList() = this.map { it.toChapter() }
