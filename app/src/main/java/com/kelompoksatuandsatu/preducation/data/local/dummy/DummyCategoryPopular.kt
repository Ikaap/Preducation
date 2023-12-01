package com.kelompoksatuandsatu.preducation.data.local.dummy

import com.kelompoksatuandsatu.preducation.model.CategoryPopular

interface DummyCategoryPopularDataSource {
    fun getCategoryPopular(): List<CategoryPopular>
}

class DummyCategoryPopularDataSourceImpl() : DummyCategoryPopularDataSource {
    override fun getCategoryPopular(): List<CategoryPopular> = listOf(
        CategoryPopular(
            nameCategoryPopular = "All"
        ),
        CategoryPopular(
            nameCategoryPopular = "Data Science"
        ),
        CategoryPopular(
            nameCategoryPopular = "UI/UX Design"
        ),
        CategoryPopular(
            nameCategoryPopular = "Android Development"
        )
    )
}
