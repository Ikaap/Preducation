package com.kelompoksatuandsatu.preducation.data.repository

import com.kelompoksatuandsatu.preducation.data.network.api.datasource.CourseDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoriesprogress.toCategoryProgressList
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoryclass.toCategoryClassList
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categorytypeclass.toCategoryTypeClassList
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.courseall.toCourseList
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.toDetailCourse
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.PaymentCourseRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.toPaymentResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.progress.courseprogress.toCourseProgressList
import com.kelompoksatuandsatu.preducation.model.category.categoryclass.CategoryClass
import com.kelompoksatuandsatu.preducation.model.category.categoryprogress.CategoryType
import com.kelompoksatuandsatu.preducation.model.course.courseall.CourseViewParam
import com.kelompoksatuandsatu.preducation.model.course.detailcourse.DetailCourseViewParam
import com.kelompoksatuandsatu.preducation.model.payment.PaymentResponseViewParam
import com.kelompoksatuandsatu.preducation.model.progress.CourseProgressItemClass
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import com.kelompoksatuandsatu.preducation.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface CourseRepository {
    fun getCategoriesClass(): Flow<ResultWrapper<List<CategoryClass>>>
    fun getCourseHome(category: String? = null): Flow<ResultWrapper<List<CourseViewParam>>>
    fun getCourseById(id: String? = null): Flow<ResultWrapper<DetailCourseViewParam>>
    suspend fun postIndexCourseById(id: String? = null, request: Int): Flow<ResultWrapper<Boolean>>
    fun getCategoriesProgress(): Flow<ResultWrapper<List<CategoryType>>>
    fun getCategoriesTypeClass(): Flow<ResultWrapper<List<CategoryType>>>
    fun getCourseUserProgress(category: String? = null): Flow<ResultWrapper<List<CourseProgressItemClass>>>
    suspend fun paymentCourse(item: DetailCourseViewParam): Flow<ResultWrapper<PaymentResponseViewParam>>
}

class CourseRepositoryImpl(
    private val apiDataSource: CourseDataSource
) : CourseRepository {
    override fun getCategoriesClass(): Flow<ResultWrapper<List<CategoryClass>>> {
        return proceedFlow {
            apiDataSource.getCategoriesClass().data?.toCategoryClassList() ?: emptyList()
        }.map {
            if (it.payload?.isEmpty() == true) {
                ResultWrapper.Empty(it.payload)
            } else {
                it
            }
        }.catch {
            emit(ResultWrapper.Error(Exception(it)))
        }.onStart {
            emit(ResultWrapper.Loading())
            delay(3000)
        }
    }

    override fun getCourseHome(category: String?): Flow<ResultWrapper<List<CourseViewParam>>> {
        return proceedFlow {
            apiDataSource.getCourseHome(category).data?.toCourseList() ?: emptyList()
        }.map {
            if (it.payload?.isEmpty() == true) {
                ResultWrapper.Empty(it.payload)
            } else {
                it
            }
        }.catch {
            emit(ResultWrapper.Error(Exception(it)))
        }.onStart {
            emit(ResultWrapper.Loading())
            delay(3000)
        }
    }

    override fun getCourseById(id: String?): Flow<ResultWrapper<DetailCourseViewParam>> {
        return proceedFlow {
            apiDataSource.getCourseById(id).data?.toDetailCourse()!!
        }.catch {
            emit(ResultWrapper.Error(Exception(it)))
        }.onStart {
            emit(ResultWrapper.Loading())
            delay(2000)
        }
    }

    override suspend fun postIndexCourseById(
        id: String?,
        request: Int
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
//            val indexReq = ProgressCourseRequest(request.index)
            apiDataSource.postIndexCourseById(id, request).success == true
        }
    }

    override suspend fun paymentCourse(item: DetailCourseViewParam): Flow<ResultWrapper<PaymentResponseViewParam>> {
        return proceedFlow {
            val paymentItemRequest = PaymentCourseRequest(item.id, item.title, item.price)

            apiDataSource.paymentCourse(paymentItemRequest).data?.toPaymentResponse()!!
        }
    }

    override fun getCategoriesProgress(): Flow<ResultWrapper<List<CategoryType>>> {
        return proceedFlow {
            val apiResult = apiDataSource.getCategoriesProgress()
            apiResult.data?.toCategoryProgressList() ?: emptyList()
        }
    }

    override fun getCourseUserProgress(category: String?): Flow<ResultWrapper<List<CourseProgressItemClass>>> {
        return proceedFlow {
            apiDataSource.getCourseUserProgress(category).data?.toCourseProgressList() ?: emptyList()
        }
    }

    override fun getCategoriesTypeClass(): Flow<ResultWrapper<List<CategoryType>>> {
        return proceedFlow {
            val apiResult = apiDataSource.getCategoriesTypeClass()
            apiResult.data?.toCategoryTypeClassList() ?: emptyList()
        }
    }
}
