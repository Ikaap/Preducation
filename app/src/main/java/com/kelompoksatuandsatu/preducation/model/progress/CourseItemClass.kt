package com.kelompoksatuandsatu.preducation.model.progress

import com.kelompoksatuandsatu.preducation.data.network.api.model.progress.courseprogress.CourseId

data class CourseProgressItemClass(

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
