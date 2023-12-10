package com.kelompoksatuandsatu.preducation.data.repository

import com.kelompoksatuandsatu.preducation.data.network.api.datasource.CourseDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoryclass.toCategoryClassList
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.toCourseList
import com.kelompoksatuandsatu.preducation.model.CategoryClass
import com.kelompoksatuandsatu.preducation.model.CategoryPopular
import com.kelompoksatuandsatu.preducation.model.Course
import com.kelompoksatuandsatu.preducation.model.CourseViewParam
import com.kelompoksatuandsatu.preducation.model.detailcourse.DetailCourseViewParam
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import com.kelompoksatuandsatu.preducation.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    fun getCategoriesClass(): Flow<ResultWrapper<List<CategoryClass>>>
    fun getCourseHome(category: String? = null): Flow<ResultWrapper<List<CourseViewParam>>>
    fun getCourseById(id: String? = null): Flow<ResultWrapper<DetailCourseViewParam>>
    fun getCategoriesProgress(): Flow<ResultWrapper<List<CategoryPopular>>>
    fun getCourseUserProgress(category: String? = null): Flow<ResultWrapper<List<Course>>>
}

class CourseRepositoryImpl(private val apiDataSource: CourseDataSource) : CourseRepository {

    override fun getCategoriesClass(): Flow<ResultWrapper<List<CategoryCourse>>> {
        return proceedFlow {
            val apiResult = apiDataSource.getCategoriesClass()
            apiResult.data?.toCategoryList() ?: emptyList()
        }
    }

    override fun getCourseHome(category: String?): Flow<ResultWrapper<List<CourseViewParam>>> {
        return proceedFlow {
            apiDataSource.getCourseHome(category).data?.toCourseList() ?: emptyList()
        }
    }

    override fun getCourseById(id: String?): Flow<ResultWrapper<DetailCourseViewParam>> {
        return proceedFlow {
            apiDataSource.getCourseById(id).data?.toDetailCourse()!!
        }
    }

    override fun getCategoriesProgress(): Flow<ResultWrapper<List<CategoryPopular>>> {
        return proceedFlow {
            val apiResult = apiDataSource.getCategoriesProgress()
            apiResult.data?.toCategoryProgressList() ?: emptyList()
        }
    }

    override fun getCourseUserProgress(category: String?): Flow<ResultWrapper<List<Course>>> {
        return proceedFlow {
            apiDataSource.getCourseUserProgress(category).data?.toCourseProgressList() ?: emptyList()
        }
    }
}
