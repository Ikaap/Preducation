package com.kelompoksatuandsatu.preducation.data.network.api.model.progress.courseprogress

import com.google.gson.annotations.SerializedName
import com.kelompoksatuandsatu.preducation.model.progress.CourseProgressItemClass

data class CourseProgressItemResponse(

    @SerializedName("createdAt")
    val createdAt: String?,

    @SerializedName("percentage")
    val percentage: Int?,

    @SerializedName("__v")
    val v: Int?,

    @SerializedName("indexProgress")
    val indexProgress: Int?,

    @SerializedName("_id")
    val id: String?,

    @SerializedName("isActive")
    val isActive: Boolean?,

    @SerializedName("userId")
    val userId: String?,

    @SerializedName("courseId")
    val courseId: CourseId?,

    @SerializedName("status")
    val status: String?
)

data class CourseId(

    @SerializedName("totalDuration")
    val totalDuration: Int?,

    @SerializedName("classCode")
    val classCode: String?,

    @SerializedName("sold")
    val sold: Int?,

    @SerializedName("thumbnail")
    val thumbnail: String?,

    @SerializedName("level")
    val level: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("totalRating")
    val totalRating: Int?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("isActive")
    val isActive: Boolean?,

    @SerializedName("totalModule")
    val totalModule: Int?,

    @SerializedName("createdAt")
    val createdAt: String?,

    @SerializedName("targetAudience")
    val targetAudience: List<String?>?,

    @SerializedName("price")
    val price: Int?,

    @SerializedName("typeClass")
    val typeClass: String?,

    @SerializedName("_id")
    val id: String?,

    @SerializedName("category")
    val category: Category?
)

data class Category(

    @SerializedName("name")
    val name: String?,

    @SerializedName("_id")
    val id: String?,

    @SerializedName("imageCategory")
    val imageCategory: String?
)

// fun CourseProgressItemResponse.toCourseProgress() = CourseProgressItemClass(
//    id = this.id.orEmpty(),
//    titleCourse = this.courseId?.title.orEmpty(),
//    nameCategoryPopular = this.courseId?.category?.name.orEmpty(),
//    thumbnail = this.courseId?.thumbnail.orEmpty(),
//    ratingCourse = this.courseId?.totalRating ?: 0,
//    levelCourse = this.courseId?.level.orEmpty(),
//    moduleCourse = this.courseId?.totalModule,
//    durationCourse = this.courseId?.totalDuration,
//    progress = this.indexProgress ?: 0,
//    statusPayment = this.status.orEmpty(),
//    type = this.courseId?.typeClass.orEmpty(),
//    priceCourse = this.courseId?.price ?: 0
// )

fun CourseProgressItemResponse.toCourseProgress() = CourseProgressItemClass(
    createdAt = this.createdAt.orEmpty(),
    percentage = this.percentage ?: 0,
    v = this.v ?: 0,
    indexProgress = this.indexProgress ?: 0,
    id = this.id.orEmpty(),
    isActive = this.isActive ?: false,
    userId = this.userId.orEmpty(),
    courseId = this.courseId,
    status = this.status.orEmpty()
)
fun Collection<CourseProgressItemResponse>.toCourseProgressList() = this.map { it.toCourseProgress() }
