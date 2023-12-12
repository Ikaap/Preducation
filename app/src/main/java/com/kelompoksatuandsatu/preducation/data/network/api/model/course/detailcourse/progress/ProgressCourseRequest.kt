package com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.progress

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ProgressCourseRequest(
    @SerializedName("indexProgress")
    val indexProgress: Int
)
