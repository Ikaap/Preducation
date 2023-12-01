package com.kelompoksatuandsatu.preducation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class PopularCourse(
    val id: String = UUID.randomUUID().toString(),
    val imgUrlPopularCourse: String,
    val nameCategoryPopular: String,
    val ratingCourse: String,
    val titleCourse: String,
    val levelCourse: String,
    val durationCourse: String,
    val moduleCourse: String,
    val priceCourse: Int
) : Parcelable
