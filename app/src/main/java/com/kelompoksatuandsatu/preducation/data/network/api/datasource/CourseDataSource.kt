package com.kelompoksatuandsatu.preducation.data.network.api.datasource

import com.kelompoksatuandsatu.preducation.data.network.api.model.categoriesclass.CategoriesClassResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.categoriesprogress.CategoriesProgressResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.courseprogress.CourseProgressResponse
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoryclass.CategoriesClassResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.CourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.DetailCourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.progress.ProgressCourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.PaymentCourseRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.PaymentCourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService

interface CourseDataSource {
    suspend fun getCategoriesClass(): CategoriesClassResponse
    suspend fun getCourseHome(category: String? = null): CourseResponse
    suspend fun paymentCourse(paymentCourseRequest: PaymentCourseRequest): PaymentCourseResponse
    suspend fun getCourseById(id: String): DetailCourseResponse

    suspend fun postIndexCourseById(id: String, progressRequest: Int): ProgressCourseResponse
  
  
    suspend fun getCourseUserProgress(category: String? = null): CourseProgressResponse
    suspend fun getCategoriesProgress(): CategoriesProgressResponse
}
class CourseDataSourceImpl(
    private val service: PreducationService
) : CourseDataSource {
    override suspend fun getCategoriesClass(): CategoriesClassResponse {
        return service.getCategoriesClass()
    }

    override suspend fun getCourseHome(category: String?): CourseResponse {
        return service.getCourseHome(category)
    }

    override suspend fun getCourseById(id: String): DetailCourseResponse {
        return service.getCourseById(id)
    }

    override suspend fun paymentCourse(paymentCourseRequest: PaymentCourseRequest): PaymentCourseResponse {
        return service.paymentCourse(paymentCourseRequest)
    }

    override suspend fun postIndexCourseById(
        id: String,
        progressRequest: Int
    ): ProgressCourseResponse {
        return service.postIndexCourseById(id, progressRequest)
    }
     override suspend fun getCourseUserProgress(category: String?): CourseProgressResponse {
        return service.getCourseUserProgress(category)
    }
    override suspend fun getCategoriesProgress(): CategoriesProgressResponse {
        return service.getCategoriesProgress()
    }
}
