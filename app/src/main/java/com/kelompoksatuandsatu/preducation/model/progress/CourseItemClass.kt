package com.kelompoksatuandsatu.preducation.model.progress

import com.kelompoksatuandsatu.preducation.data.network.api.model.progress.courseprogress.CourseId

data class CourseProgressItemClass(
//    val durationCourse: Int?,
//    val thumbnail: String?,
//    val levelCourse: String?,
//    val ratingCourse: Int?,
//    val titleCourse: String?,
//    val moduleCourse: Int?,
//    val priceCourse: Int?,
//    val type: String?,
//    val id: String?,
//    val nameCategoryPopular: String?,
//    val progress: Int?,
//    val statusPayment: String?

    val createdAt: String?,

    val percentage: Int?,

    val v: Int?,

    val indexProgress: Int?,

    val id: String?,

    val isActive: Boolean?,

    val userId: String?,

    val courseId: CourseId?,

    val status: String?
)
