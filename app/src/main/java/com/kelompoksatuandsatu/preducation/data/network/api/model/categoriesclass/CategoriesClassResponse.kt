package com.kelompoksatuandsatu.preducation.data.network.api.model.categoriesclass

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CategoriesClassResponse(
	@SerializedName("success")
	val success: Boolean?,

	@SerializedName("data")
	val data: List<CategoriesClassItemResponse>?,

	@SerializedName("message")
	val message: String?,

	@SerializedName("status")
	val status: String?
)
