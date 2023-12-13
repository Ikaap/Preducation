package com.kelompoksatuandsatu.preducation.data.repository

import com.kelompoksatuandsatu.preducation.data.network.api.datasource.CourseDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoryclass.toCategoryClassList
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.toDetailCourse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.toCourseList
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.PaymentCourseRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.toPaymentResponse
import com.kelompoksatuandsatu.preducation.model.CategoryClass
import com.kelompoksatuandsatu.preducation.model.CourseViewParam
import com.kelompoksatuandsatu.preducation.model.PaymentResponseViewParam
import com.kelompoksatuandsatu.preducation.model.detailcourse.DetailCourseViewParam
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import com.kelompoksatuandsatu.preducation.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart

interface CourseRepository {
    fun getCategoriesClass(): Flow<ResultWrapper<List<CategoryClass>>>
    fun getCourseHome(category: String? = null): Flow<ResultWrapper<List<CourseViewParam>>>

    fun getCourseById(id: String): Flow<ResultWrapper<DetailCourseViewParam>>

    suspend fun postIndexCourseById(
        id: String,
        request: Int
    ): Flow<ResultWrapper<Boolean>>

    suspend fun paymentCourse(item: DetailCourseViewParam): Flow<ResultWrapper<PaymentResponseViewParam>>
}

class CourseRepositoryImpl(
    private val apiDataSource: CourseDataSource
) : CourseRepository {
    override fun getCategoriesClass(): Flow<ResultWrapper<List<CategoryClass>>> {
        return proceedFlow {
            apiDataSource.getCategoriesClass().data?.toCategoryClassList() ?: emptyList()
        }
    }

    override fun getCourseHome(category: String?): Flow<ResultWrapper<List<CourseViewParam>>> {
        return proceedFlow {
            apiDataSource.getCourseHome(category).data?.toCourseList() ?: emptyList()
        }
    }

    override fun getCourseById(id: String): Flow<ResultWrapper<DetailCourseViewParam>> {
        return proceedFlow {
            apiDataSource.getCourseById(id).data?.toDetailCourse()!!
        }.catch {
            emit(ResultWrapper.Error(Exception(it)))
        }.onStart {
            emit(ResultWrapper.Loading())
            delay(2000)
        }
    }

    override suspend fun paymentCourse(item: DetailCourseViewParam): Flow<ResultWrapper<PaymentResponseViewParam>> {
        return proceedFlow {
            val paymentItemRequest = PaymentCourseRequest(item.id, item.title, item.price)

            apiDataSource.paymentCourse(paymentItemRequest).data?.toPaymentResponse()!!
        }
    }

    override suspend fun postIndexCourseById(
        id: String,
        request: Int
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
//            val indexReq = ProgressCourseRequest(request.index)
            apiDataSource.postIndexCourseById(id, request).success == true
        }
    }
}
