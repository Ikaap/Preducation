package com.kelompoksatuandsatu.preducation.data.network.api.datasource

import com.kelompoksatuandsatu.preducation.data.network.api.model.categoriesclass.CategoriesClassResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.categoriesprogress.CategoriesProgressResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.courseprogress.CourseProgressResponse
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService

interface CourseDataSource {
    suspend fun getCourseUserProgress(category: String? = null): CourseProgressResponse
    suspend fun getCategoriesClass(): CategoriesClassResponse
    suspend fun getCategoriesProgress(): CategoriesProgressResponse
}

class CourseApiDataSource(private val service: PreducationService) : CourseDataSource {
    override suspend fun getCourseUserProgress(category: String?): CourseProgressResponse {
        return service.getCourseUserProgress(category)
    }

    override suspend fun getCategoriesClass(): CategoriesClassResponse {
        return service.getCategoriesClass()
    }

    override suspend fun getCategoriesProgress(): CategoriesProgressResponse {
        return service.getCategoriesProgress()
    }
}
