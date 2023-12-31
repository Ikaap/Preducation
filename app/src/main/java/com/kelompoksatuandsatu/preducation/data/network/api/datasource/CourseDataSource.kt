package com.kelompoksatuandsatu.preducation.data.network.api.datasource

import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoriesprogress.CategoriesProgressResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoryclass.CategoriesClassResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categorytypeclass.CategoriesTypeClassResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.courseall.CourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.DetailCourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.progress.ProgressCourseRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.progress.ProgressCourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.PaymentCourseRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.PaymentCourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history.HistoryItemResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.progress.courseprogress.CourseProgressResponse
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService

interface CourseDataSource {
    suspend fun getCategoriesClass(): CategoriesClassResponse
    suspend fun getCourseHome(category: String? = null, typeClass: String? = null, title: String? = null): CourseResponse
    suspend fun getCourseHomeFilter(category: List<String>? = null, typeClass: String? = null, title: String? = null): CourseResponse
    suspend fun getCourseById(id: String): DetailCourseResponse
    suspend fun getCourseUserProgress(status: String? = null): CourseProgressResponse
    suspend fun getCategoriesProgress(): CategoriesProgressResponse
    suspend fun getCategoriesTypeClass(): CategoriesTypeClassResponse
    suspend fun postIndexCourseById(id: String, progressRequest: ProgressCourseRequest): ProgressCourseResponse

    suspend fun paymentCourse(paymentCourseRequest: PaymentCourseRequest): PaymentCourseResponse
    suspend fun getHistoryPayment(): HistoryItemResponse
}
class CourseDataSourceImpl(
    private val service: PreducationService
) : CourseDataSource {
    override suspend fun getCategoriesClass(): CategoriesClassResponse {
        return service.getCategoriesClass()
    }

    override suspend fun getCourseHome(category: String?, typeClass: String?, title: String?): CourseResponse {
        return service.getCourseHome(category, typeClass, title)
    }

    override suspend fun getCourseHomeFilter(
        category: List<String>?,
        typeClass: String?,
        title: String?
    ): CourseResponse {
        return service.getCourseHomeFilter(category, typeClass, title)
    }

    override suspend fun paymentCourse(paymentCourseRequest: PaymentCourseRequest): PaymentCourseResponse {
        return service.paymentCourse(paymentCourseRequest)
    }

    override suspend fun getCourseUserProgress(status: String?): CourseProgressResponse {
        return service.getCourseUserProgress(status)
    }

    override suspend fun getCategoriesProgress(): CategoriesProgressResponse {
        return service.getCategoriesProgress()
    }

    override suspend fun getCategoriesTypeClass(): CategoriesTypeClassResponse {
        return service.getCategoriesTypeClass()
    }

    override suspend fun getCourseById(id: String): DetailCourseResponse {
        return service.getCourseById(id)
    }

    override suspend fun postIndexCourseById(
        id: String,
        progressRequest: ProgressCourseRequest
    ): ProgressCourseResponse {
        return service.postIndexCourseById(id, progressRequest)
    }

    override suspend fun getHistoryPayment(): HistoryItemResponse {
        return service.getHistoryPayment()
    }
}
