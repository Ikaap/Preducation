package com.kelompoksatuandsatu.preducation.data.local.dummy

import com.kelompoksatuandsatu.preducation.model.CategoryPopular

interface DummyCategoryPopularDataSource {
    fun getCategoryPopular(): List<CategoryPopular>
    fun getCategoryProgress(): List<CategoryPopular>
    fun getCategoryType(): List<CategoryPopular>
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

    override fun getCategoryProgress(): List<CategoryPopular> = listOf(
        CategoryPopular(
            nameCategoryPopular = "All"
        ),
        CategoryPopular(
            nameCategoryPopular = "In Progress"
        ),
        CategoryPopular(
            nameCategoryPopular = "Finish"
        )
    )

    override fun getCategoryType(): List<CategoryPopular> = listOf(
        CategoryPopular(
            nameCategoryPopular = "All"
        ),
        CategoryPopular(
            nameCategoryPopular = "Premium"
        ),
        CategoryPopular(
            nameCategoryPopular = "Freemium"
        )
    )
}
