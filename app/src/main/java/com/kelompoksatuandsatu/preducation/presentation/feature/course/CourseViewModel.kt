package com.kelompoksatuandsatu.preducation.presentation.feature.course

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepository
import com.kelompoksatuandsatu.preducation.model.category.categoryprogress.CategoryType
import com.kelompoksatuandsatu.preducation.model.course.courseall.CourseViewParam
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CourseViewModel(
    private val repositoryCourse: CourseRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {

    private val _course = MutableLiveData<ResultWrapper<List<CourseViewParam>>>()
    val course: LiveData<ResultWrapper<List<CourseViewParam>>>
        get() = _course

    fun getCourse(category: String? = null, typeClass: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryCourse.getCourseHome(category, if (typeClass == "All") null else typeClass?.toLowerCase())
                .collect {
                    _course.postValue(it)
                }
        }
    }

    private val _categoriesTypeClass = MutableLiveData<ResultWrapper<List<CategoryType>>>()
    val categoriesTypeClass: LiveData<ResultWrapper<List<CategoryType>>>
        get() = _categoriesTypeClass

    fun getCategoriesTypeClass() {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryCourse.getCategoriesTypeClass().collect {
                _categoriesTypeClass.postValue(it)
            }
        }
    }

    private val _isUserLogin = MutableLiveData<Boolean>()
    val isUserLogin: LiveData<Boolean>
        get() = _isUserLogin

    fun checkLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            val userStatus = userPreferenceDataSource.getUserToken().firstOrNull() != null
            _isUserLogin.postValue(userStatus)
        }
    }
}
