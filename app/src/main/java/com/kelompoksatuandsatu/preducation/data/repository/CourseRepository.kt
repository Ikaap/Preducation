package com.kelompoksatuandsatu.preducation.data.repository

import com.kelompoksatuandsatu.preducation.data.network.api.datasource.CourseDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoriesprogress.toCategoryProgressList
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoryclass.toCategoryClassList
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categorytypeclass.toCategoryTypeClassList
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.courseall.toCourseList
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.progress.ProgressCourseRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.toDetailCourse
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.PaymentCourseRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history.CourseItem
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history.toCourseItemList
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.toPaymentResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.progress.courseprogress.toCourseProgressList
import com.kelompoksatuandsatu.preducation.model.category.categoryclass.CategoryClass
import com.kelompoksatuandsatu.preducation.model.category.categoryprogress.CategoryType
import com.kelompoksatuandsatu.preducation.model.course.courseall.CourseViewParam
import com.kelompoksatuandsatu.preducation.model.course.detailcourse.DetailCourseViewParam
import com.kelompoksatuandsatu.preducation.model.course.detailcourse.VideoViewParam
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
    fun getCourseHome(category: String? = null, typeClass: String? = null, title: String? = null): Flow<ResultWrapper<List<CourseViewParam>>>
    fun getCourseHome(category: List<String>? = null, typeClass: String? = null, title: String? = null): Flow<ResultWrapper<List<CourseViewParam>>>
    suspend fun postIndexCourseById(id: String, request: VideoViewParam): Flow<ResultWrapper<Boolean>>
    fun getCategoriesProgress(): Flow<ResultWrapper<List<CategoryType>>>
    fun getCategoriesTypeClass(): Flow<ResultWrapper<List<CategoryType>>>
    fun getCourseUserProgress(status: String? = null): Flow<ResultWrapper<List<CourseProgressItemClass>>>
    suspend fun paymentCourse(item: DetailCourseViewParam): Flow<ResultWrapper<PaymentResponseViewParam>>
    fun getCourseById(id: String): Flow<ResultWrapper<DetailCourseViewParam>>
    suspend fun getHistoryPayment(): Flow<ResultWrapper<List<CourseItem>>>
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

    override fun getCourseHome(category: String?, typeClass: String?, title: String?): Flow<ResultWrapper<List<CourseViewParam>>> {
        return proceedFlow {
            apiDataSource.getCourseHome(category, typeClass, title).data?.toCourseList() ?: emptyList()
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

    override fun getCourseHome(
        category: List<String>?,
        typeClass: String?,
        title: String?
    ): Flow<ResultWrapper<List<CourseViewParam>>> {
        return proceedFlow {
            apiDataSource.getCourseHome(category, typeClass, title).data?.toCourseList() ?: emptyList()
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

    override suspend fun postIndexCourseById(
        id: String,
        request: VideoViewParam
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            val indexReq = ProgressCourseRequest(request.index)
            apiDataSource.postIndexCourseById(id, indexReq).success == true
        }
    }

    override suspend fun paymentCourse(item: DetailCourseViewParam): Flow<ResultWrapper<PaymentResponseViewParam>> {
        return proceedFlow {
            val ppn = item.price?.times(0.11)!!.toInt()
            val paymentItemRequest = PaymentCourseRequest(item.id, item.title, item.price + ppn)
            apiDataSource.paymentCourse(paymentItemRequest).toPaymentResponse()
        }.catch {
            emit(ResultWrapper.Error(Exception(it)))
        }.onStart {
            emit(ResultWrapper.Loading())
            delay(2000)
        }
    }

    override fun getCategoriesProgress(): Flow<ResultWrapper<List<CategoryType>>> {
        return proceedFlow {
            apiDataSource.getCategoriesProgress().data?.toCategoryProgressList() ?: emptyList()
        }.catch {
            emit(ResultWrapper.Error(Exception(it)))
        }.onStart {
            emit(ResultWrapper.Loading())
            delay(2000)
        }
    }

    override fun getCourseUserProgress(status: String?): Flow<ResultWrapper<List<CourseProgressItemClass>>> {
        return proceedFlow {
            apiDataSource.getCourseUserProgress(status).data?.toCourseProgressList() ?: emptyList()
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

    override fun getCategoriesTypeClass(): Flow<ResultWrapper<List<CategoryType>>> {
        return proceedFlow {
            apiDataSource.getCategoriesTypeClass().data?.toCategoryTypeClassList() ?: emptyList()
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
            delay(2000)
        }
    }
    override suspend fun getHistoryPayment(): Flow<ResultWrapper<List<CourseItem>>> {
        return proceedFlow {
            apiDataSource.getHistoryPayment().data?.toCourseItemList() ?: emptyList()
        }
    }
}
