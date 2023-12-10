package com.kelompoksatuandsatu.preducation.data.network.api.datasource

import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoryclass.CategoriesClassResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.CourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.DetailCourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.PaymentCourseRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.PaymentCourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService

interface CourseDataSource {
    suspend fun getCategoriesClass(): CategoriesClassResponse
    suspend fun getCourseHome(category: String? = null): CourseResponse
    suspend fun getCourseById(id: String? = null): DetailCourseResponse

    suspend fun paymentCourse(paymentCourseRequest: PaymentCourseRequest): PaymentCourseResponse
}
class CourseDataSourceImpl(private val service: PreducationService) : CourseDataSource {
    override suspend fun getCategoriesClass(): CategoriesClassResponse {
        return service.getCategoriesClass()
    }

    override suspend fun getCourseHome(category: String?): CourseResponse {
        return service.getCourseHome(category)
    }

    override suspend fun getCourseById(id: String?): DetailCourseResponse {
        return service.getCourseById(id)
    }

    override suspend fun paymentCourse(paymentCourseRequest: PaymentCourseRequest): PaymentCourseResponse {
        return service.paymentCourse(paymentCourseRequest)
    }
}
