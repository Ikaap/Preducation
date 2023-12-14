package com.kelompoksatuandsatu.preducation.data.network.api.model.category.categorytypeclass

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.kelompoksatuandsatu.preducation.model.category.categoryprogress.CategoryType

@Keep
data class Data(
    @SerializedName("name")
    val name: String?
)

fun Data.toCategoryTypeClass() = CategoryType(
    nameCategory = this.name.orEmpty()
)

fun Collection<Data>.toCategoryTypeClassList() = this.map {
    it.toCategoryTypeClass()
}
