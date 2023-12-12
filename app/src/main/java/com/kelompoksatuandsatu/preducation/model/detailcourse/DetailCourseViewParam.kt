package com.kelompoksatuandsatu.preducation.model.detailcourse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailCourseViewParam(
    val category: CategoryDetailCourse?,
    val chapters: List<ChapterViewParam>?,
    val classCode: String?,
    val createdAt: String?,
//    val createdBy: CreatedBy?,
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
    val updatedAt: String?,
    val thumbnail: String?
) : Parcelable
fun DetailCourseViewParam.toTargetAudience() = TargetAudience(
    desc = this.targetAudience.orEmpty()
)

// fun Collection<DetailCourse>.toTargetList() = this.map { it.toTargetAudience() }
