package com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoriesprogress

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.kelompoksatuandsatu.preducation.model.category.categoryprogress.CategoryType

@Keep
data class CategoriesProgressItemResponse(
    @SerializedName("name")
    val name: String?
)

fun CategoriesProgressItemResponse.toCategoryProgress() = CategoryType(
    nameCategory = this.name.orEmpty()
)

fun Collection<CategoriesProgressItemResponse>.toCategoryProgressList() = this.map {
    it.toCategoryProgress()
}
