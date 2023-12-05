package com.kelompoksatuandsatu.preducation.data.repository

import com.kelompoksatuandsatu.preducation.data.network.api.datasource.CourseDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoryclass.toCategoryClassList
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.toCourseList
import com.kelompoksatuandsatu.preducation.model.CategoryClass
import com.kelompoksatuandsatu.preducation.model.Course
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import com.kelompoksatuandsatu.preducation.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    fun getCategoriesClass(): Flow<ResultWrapper<List<CategoryClass>>>
    fun getCourseHome(category: String? = null): Flow<ResultWrapper<List<Course>>>
}

class CourseRepositoryImpl(private val apiDataSource: CourseDataSource) : CourseRepository {
    override fun getCategoriesClass(): Flow<ResultWrapper<List<CategoryClass>>> {
        return proceedFlow {
            apiDataSource.getCategoriesClass().data?.toCategoryClassList() ?: emptyList()
        }
    }

    override fun getCourseHome(category: String?): Flow<ResultWrapper<List<Course>>> {
        return proceedFlow {
            apiDataSource.getCourseHome(category).data?.toCourseList() ?: emptyList()
        }
    }
}
