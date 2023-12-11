package com.kelompoksatuandsatu.preducation.data.network.api.datasource

import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoryclass.CategoriesClassResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.CourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.DetailCourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.courseprogress.CourseProgressResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.progress.ProgressCourseRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.progress.ProgressCourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService

interface CourseDataSource {
    suspend fun getCourseUserProgress(category: String? = null): CourseProgressResponse
    suspend fun getCategoriesClass(): CategoriesClassResponse
    suspend fun getCourseHome(category: String? = null): CourseResponse
    suspend fun getCourseById(id: String? = null, token: String): DetailCourseResponse
    suspend fun getCategoriesProgress(): CategoriesProgressResponse
    suspend fun postIndexCourseById(id: String? = null, progressRequest: ProgressCourseRequest): ProgressCourseResponse
}
class CourseDataSourceImpl(
    private val service: PreducationService,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : CourseDataSource {
    override suspend fun getCategoriesClass(): CategoriesClassResponse {
        return service.getCategoriesClass()
    }

    override suspend fun getCourseUserProgress(category: String?): CourseProgressResponse {
        return service.getCourseUserProgress(category)
    }

    override suspend fun getCategoriesProgress(): CategoriesProgressResponse {
        return service.getCategoriesProgress()
    }

    override suspend fun getCourseById(id: String?, token: String): DetailCourseResponse {
        return service.getCourseById(id, token)
    }

    override suspend fun getCourseHome(category: String?): CourseResponse {
        return service.getCourseHome(category)
    }

    override suspend fun postIndexCourseById(
        id: String?,
        progressRequest: ProgressCourseRequest
    ): ProgressCourseResponse {
        return service.postIndexCourseById(id, progressRequest)
    }
}
