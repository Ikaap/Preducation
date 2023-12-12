package com.kelompoksatuandsatu.preducation.data.network.api.model.categoriesprogress

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.kelompoksatuandsatu.preducation.model.CategoryPopular

@Keep
data class CategoriesProgressItemResponse(
	@SerializedName("name")
	val name: String?
)

fun CategoriesProgressItemResponse.toCategoryProgress() = CategoryPopular(
	nameCategoryPopular = this.name.orEmpty()
)

fun Collection<CategoriesProgressItemResponse>.toCategoryProgressList() = this.map {
	it.toCategoryProgress()
}
