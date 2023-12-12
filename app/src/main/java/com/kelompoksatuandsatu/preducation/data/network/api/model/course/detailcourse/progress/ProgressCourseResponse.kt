package com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.progress

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ProgressCourseResponse(
    @SerializedName("data")
    val data: DataX?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)
