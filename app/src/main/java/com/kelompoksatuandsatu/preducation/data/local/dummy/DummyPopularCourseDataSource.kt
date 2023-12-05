package com.kelompoksatuandsatu.preducation.data.local.dummy

import com.kelompoksatuandsatu.preducation.model.Course

interface DummyPopularCourseDataSource {
    fun getPopularCourse(): List<Course>
}

// class DummyPopularCourseDataSourceImpl() : DummyPopularCourseDataSource {
//    override fun getPopularCourse(): List<Course> = listOf(
//        Course(
//            imgUrlPopularCourse = "https://raw.githubusercontent.com/NindaNuraisyah/AssetFinalProject/master/app/src/main/res/drawable/img_uiux_design.png",
//            nameCategoryPopular = "Data Science",
//            ratingCourse = "4.7",
//            titleCourse = "Belajar data cleaning dan sorting dengan Phyton",
//            levelCourse = "Adanvanced Level",
//            durationCourse = "60 menit",
//            moduleCourse = "19 Modul",
//            priceCourse = 249000,
//            progress = 40,
//            type = "Freemium",
//            statusPayment = "Waiting"
//        ),
//        Course(
//            imgUrlPopularCourse = "https://raw.githubusercontent.com/NindaNuraisyah/AssetFinalProject/master/app/src/main/res/drawable/img_product_management.png",
//            nameCategoryPopular = "Data Science",
//            ratingCourse = "4.5",
//            titleCourse = "Belajar Dasar Pemrogranan Kotlin Dengan Android Studio",
//            levelCourse = "Beginner Level",
//            durationCourse = "45 menit",
//            moduleCourse = "8 Modul",
//            priceCourse = 199000,
//            progress = 40,
//            type = "Premium",
//            statusPayment = "Paid"
//        ),
//        Course(
//            imgUrlPopularCourse = "https://raw.githubusercontent.com/NindaNuraisyah/AssetFinalProject/master/app/src/main/res/drawable/img_web_development.png",
//            nameCategoryPopular = "Data Science",
//            ratingCourse = "4.3",
//            titleCourse = "Belajar dasar pemrograman Web",
//            levelCourse = "Adanvanced Level",
//            durationCourse = "120 menit",
//            moduleCourse = "20 Modul",
//            priceCourse = 185000,
//            progress = 40,
//            type = "Freemium",
//            statusPayment = "Paid"
//        ),
//        Course(
//            imgUrlPopularCourse = "https://raw.githubusercontent.com/NindaNuraisyah/AssetFinalProject/master/app/src/main/res/drawable/img_android_development.png",
//            nameCategoryPopular = "Data Science",
//            ratingCourse = "4.6",
//            titleCourse = "Intro to Basic Web Developer ",
//            levelCourse = "Adanvanced Level",
//            durationCourse = "90 menit",
//            moduleCourse = "6 Modul",
//            priceCourse = 239000,
//            progress = 40,
//            type = "Premium",
//            statusPayment = "Paid"
//        )
//    )
// }
