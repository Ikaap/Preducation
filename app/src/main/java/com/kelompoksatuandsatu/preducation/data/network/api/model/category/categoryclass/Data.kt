package com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoryclass

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.kelompoksatuandsatu.preducation.model.CategoryClass

@Keep
data class Data(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("imageCategory")
    val imageCategory: String?,
    @SerializedName("isActive")
    val isActive: Boolean?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("__v")
    val v: Int?
)

fun Data.toCategoryClass() = CategoryClass(
    createdAt = this.createdAt.orEmpty(),
    id = this.id.orEmpty(),
    imageCategory = this.imageCategory.orEmpty(),
    isActive = this.isActive ?: false,
    name = this.name.orEmpty(),
    v = this.v ?: 0
)

fun Collection<Data>.toCategoryClassList() = this.map {
    it.toCategoryClass()
}
