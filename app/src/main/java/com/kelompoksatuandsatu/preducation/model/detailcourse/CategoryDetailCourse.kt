package com.kelompoksatuandsatu.preducation.model.detailcourse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryDetailCourse(
    val id: String?,
    val name: String?
) : Parcelable
