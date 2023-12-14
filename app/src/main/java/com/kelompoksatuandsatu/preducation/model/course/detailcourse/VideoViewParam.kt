package com.kelompoksatuandsatu.preducation.model.course.detailcourse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoViewParam(
    val duration: Int?,
    val id: String?,
    val index: Int?,
    val title: String?,
    val videoUrl: String?
) : Parcelable
