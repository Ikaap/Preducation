package com.kelompoksatuandsatu.preducation.model.category.categoryclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryClass(
    val createdAt: String,
    val id: String,
    val imageCategory: String,
    val isActive: Boolean,
    val name: String,
    val v: Int
) : Parcelable
