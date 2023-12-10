package com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.kelompoksatuandsatu.preducation.model.detailcourse.DetailCourseViewParam

@Keep
data class Data(
    @SerializedName("category")
    val category: Category?,
    @SerializedName("chapters")
    val chapters: List<Chapter>?,
    @SerializedName("classCode")
    val classCode: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("createdBy")
    val createdBy: CreatedBy?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("isActive")
    val isActive: Boolean?,
    @SerializedName("level")
    val level: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("sold")
    val sold: Int?,
    @SerializedName("targetAudience")
    val targetAudience: List<String>?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("totalDuration")
    val totalDuration: Int?,
    @SerializedName("totalModule")
    val totalModule: Int?,
    @SerializedName("totalRating")
    val totalRating: Int?,
    @SerializedName("typeClass")
    val typeClass: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)

fun Data.toDetailCourse() = DetailCourseViewParam(
    category = this.category?.toCategory(),
    chapters = this.chapters?.toChapterList(),
    classCode = this.classCode.orEmpty(),
    createdAt = this.createdAt.orEmpty(),
//    createdBy = this.createdBy,
    description = this.description.orEmpty(),
    id = this.id.orEmpty(),
    isActive = this.isActive ?: false,
    level = this.level.orEmpty(),
    price = this.price ?: 0,
    sold = this.sold ?: 0,
    targetAudience = this.targetAudience.orEmpty(),
    title = this.title.orEmpty(),
    totalDuration = this.totalDuration ?: 0,
    totalModule = this.totalModule ?: 0,
    totalRating = this.totalRating ?: 0,
    typeClass = this.typeClass.orEmpty(),
    updatedAt = this.updatedAt.orEmpty()
)
