package com.kelompoksatuandsatu.preducation.model.category.categoryprogress

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class CategoryType(
    val id: String = UUID.randomUUID().toString(),
    val nameCategory: String
) : Parcelable
