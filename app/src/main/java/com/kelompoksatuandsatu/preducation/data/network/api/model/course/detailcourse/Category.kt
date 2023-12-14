package com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.kelompoksatuandsatu.preducation.model.course.detailcourse.CategoryDetailCourse

@Keep
data class Category(
    @SerializedName("_id")
    val id: String?,
    @SerializedName("name")
    val name: String?
)

fun Category.toCategory() = CategoryDetailCourse(
    id = this.id.orEmpty(),
    name = this.name.orEmpty()
)
