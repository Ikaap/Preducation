package com.kelompoksatuandsatu.preducation.data.repository

import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.CourseDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoryclass.toCategoryClassList
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.progress.ProgressCourseRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.toDetailCourse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.toCourseList
import com.kelompoksatuandsatu.preducation.model.CategoryClass
import com.kelompoksatuandsatu.preducation.model.CourseViewParam
import com.kelompoksatuandsatu.preducation.model.detailcourse.DetailCourseViewParam
import com.kelompoksatuandsatu.preducation.model.detailcourse.VideoViewParam
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

    suspend fun postIndexCourseById(
        id: String? = null,
        request: VideoViewParam
    ): Flow<ResultWrapper<Boolean>>
}

class CourseRepositoryImpl(
    private val apiDataSource: CourseDataSource,
    private val userPreferenceDataSource: UserPreferenceDataSource
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
//        return proceedFlow {
//            apiDataSource.getCategoriesClass().data?.toCategoryClassList() ?: emptyList()
//        }.map {
//            if (it.payload?.isEmpty() == true) {
//                ResultWrapper.Empty(it.payload)
//            } else {
//                it
//            }
//        }.onStart {
//            emit(ResultWrapper.Loading())
//            delay(3000)
//        }
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
            delay(3000)
        }
    }

    override suspend fun postIndexCourseById(
        id: String?,
        request: VideoViewParam
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            val indexReq = ProgressCourseRequest(request.index)
            apiDataSource.postIndexCourseById(id, indexReq).success == true
        }
    }
}
