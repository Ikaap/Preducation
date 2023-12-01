package com.kelompoksatuandsatu.preducation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class CategoryCourse(
    val id: String = UUID.randomUUID().toString(),
    val nameCategoryCourse: String,
    val imgUrlCategoryCourse: String
) : Parcelable
