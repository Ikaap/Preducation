package com.kelompoksatuandsatu.preducation.data.repository

import com.kelompoksatuandsatu.preducation.data.network.api.datasource.CourseDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.categoriesclass.toCategoryList
import com.kelompoksatuandsatu.preducation.data.network.api.model.categoriesprogress.toCategoryProgressList
import com.kelompoksatuandsatu.preducation.data.network.api.model.courseprogress.toCourseProgressList
import com.kelompoksatuandsatu.preducation.model.CategoryCourse
import com.kelompoksatuandsatu.preducation.model.CategoryPopular
import com.kelompoksatuandsatu.preducation.model.Course
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import com.kelompoksatuandsatu.preducation.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    fun getCategoriesClass(): Flow<ResultWrapper<List<CategoryCourse>>>
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
