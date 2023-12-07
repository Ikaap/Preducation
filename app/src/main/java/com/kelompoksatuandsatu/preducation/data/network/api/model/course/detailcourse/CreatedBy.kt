package com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CreatedBy(
    @SerializedName("_id")
    val id: String?,
    @SerializedName("name")
    val name: String?
)
