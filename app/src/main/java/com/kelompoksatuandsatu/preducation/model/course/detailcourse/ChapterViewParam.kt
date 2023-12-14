package com.kelompoksatuandsatu.preducation.model.course.detailcourse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChapterViewParam(
    val createdAt: String?,
    val id: String?,
    val isActive: Boolean?,
    val title: String?,
    val totalDuration: Int?,
    val videos: List<VideoViewParam>?
) : Parcelable
