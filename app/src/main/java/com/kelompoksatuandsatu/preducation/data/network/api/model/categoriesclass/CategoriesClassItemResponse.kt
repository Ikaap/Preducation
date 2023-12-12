package com.kelompoksatuandsatu.preducation.data.network.api.model.categoriesclass

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.kelompoksatuandsatu.preducation.model.CategoryCourse

@Keep
data class CategoriesClassItemResponse(
	@SerializedName("createdAt")
	val createdAt: String?,

	@SerializedName("__v")
	val v: Int?,

	@SerializedName("name")
	val name: String?,

	@SerializedName("_id")
	val id: String?,

	@SerializedName("imageCategory")
	val imageCategory: String?,

	@SerializedName("isActive")
	val isActive: Boolean?
)

fun CategoriesClassItemResponse.toCategory() = CategoryCourse(
	imgUrlCategoryCourse = this.imageCategory.orEmpty(),
	nameCategoryCourse = this.name.orEmpty()
)

fun Collection<CategoriesClassItemResponse>.toCategoryList() = this.map {
	it.toCategory()
}