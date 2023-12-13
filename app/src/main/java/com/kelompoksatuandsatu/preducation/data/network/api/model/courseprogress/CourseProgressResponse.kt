package com.kelompoksatuandsatu.preducation.data.network.api.model.courseprogress

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CourseProgressResponse(

    @SerializedName("data")
    val data: List<CourseProgressItemResponse>?,

    @SerializedName("success")
    val success: Boolean?,

    @SerializedName("message")
    val message: String?
)
