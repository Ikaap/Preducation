package com.kelompoksatuandsatu.preducation.model.course.courseall

import com.kelompoksatuandsatu.preducation.data.network.api.model.course.courseall.Category
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.courseall.CreatedBy

data class CourseViewParam(
    val category: Category?,
    val classCode: String?,
    val createdAt: String?,
    val createdBy: CreatedBy?,
    val description: String?,
    val id: String?,
    val isActive: Boolean?,
    val level: String?,
    val price: Int?,
    val sold: Int?,
    val targetAudience: List<String?>?,
    val thumbnail: String?,
    val title: String?,
    val totalDuration: Double?,
    val totalModule: Int?,
    val totalRating: Double?,
    val typeClass: String?
)
