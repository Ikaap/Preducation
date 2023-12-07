package com.kelompoksatuandsatu.preducation.model

import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.Category
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.Chapter
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.CreatedBy

data class DetailCourseViewParam(
    val category: Category?,
    val chapters: List<Chapter>,
    val classCode: String?,
    val createdAt: String?,
    val createdBy: CreatedBy?,
    val description: String?,
    val id: String?,
    val isActive: Boolean?,
    val level: String?,
    val price: Int?,
    val sold: Int?,
    val targetAudience: List<String>?,
    val title: String?,
    val totalDuration: Int?,
    val totalModule: Int?,
    val totalRating: Int?,
    val typeClass: String?,
    val updatedAt: String?
)

fun DetailCourseViewParam.toTargetAudience() = TargetAudience(
    desc = this.targetAudience.orEmpty()
)

// fun Collection<DetailCourse>.toTargetList() = this.map { it.toTargetAudience() }
