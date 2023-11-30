package com.kelompoksatuandsatu.preducation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class CategoryPopular(
    val id: String = UUID.randomUUID().toString(),
    val nameCategoryPopular: String
) : Parcelable
