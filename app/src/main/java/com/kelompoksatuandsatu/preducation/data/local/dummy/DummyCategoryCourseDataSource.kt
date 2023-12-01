package com.kelompoksatuandsatu.preducation.data.local.dummy

import com.kelompoksatuandsatu.preducation.model.CategoryCourse

interface DummyCategoryCourseDataSource {
    fun getCategoryCourse(): List<CategoryCourse>
}

class DummyCategoryCourseDataSourceImpl() : DummyCategoryCourseDataSource {
    override fun getCategoryCourse(): List<CategoryCourse> = listOf(
        CategoryCourse(
            imgUrlCategoryCourse = "https://raw.githubusercontent.com/NindaNuraisyah/AssetFinalProject/master/app/src/main/res/drawable/img_uiux_design.png",
            nameCategoryCourse = "UI/UX Design"
        ),
        CategoryCourse(
            imgUrlCategoryCourse = "https://raw.githubusercontent.com/NindaNuraisyah/AssetFinalProject/master/app/src/main/res/drawable/img_product_management.png",
            nameCategoryCourse = "Product Management"
        ),
        CategoryCourse(
            imgUrlCategoryCourse = "https://raw.githubusercontent.com/NindaNuraisyah/AssetFinalProject/master/app/src/main/res/drawable/img_web_development.png",
            nameCategoryCourse = "Web Development"
        ),
        CategoryCourse(
            imgUrlCategoryCourse = "https://raw.githubusercontent.com/NindaNuraisyah/AssetFinalProject/master/app/src/main/res/drawable/img_android_development.png",
            nameCategoryCourse = "Android Development"
        ),
        CategoryCourse(
            imgUrlCategoryCourse = "https://raw.githubusercontent.com/NindaNuraisyah/AssetFinalProject/master/app/src/main/res/drawable/img_data_science.png",
            nameCategoryCourse = "Data Science"
        ),
        CategoryCourse(
            imgUrlCategoryCourse = "https://raw.githubusercontent.com/NindaNuraisyah/AssetFinalProject/master/app/src/main/res/drawable/img_ios_development.png",
            nameCategoryCourse = "IOS Development"
        ),
        CategoryCourse(
            imgUrlCategoryCourse = "https://raw.githubusercontent.com/NindaNuraisyah/AssetFinalProject/master/app/src/main/res/drawable/img_visual_design.png",
            nameCategoryCourse = "Visual Design"
        ),
        CategoryCourse(
            imgUrlCategoryCourse = "https://raw.githubusercontent.com/NindaNuraisyah/AssetFinalProject/master/app/src/main/res/drawable/img_amazon_aws.png",
            nameCategoryCourse = "Amazon AWS"
        )

    )
}
