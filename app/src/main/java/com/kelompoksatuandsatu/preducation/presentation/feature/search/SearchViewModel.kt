package com.kelompoksatuandsatu.preducation.presentation.feature.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepository
import com.kelompoksatuandsatu.preducation.model.course.courseall.CourseViewParam
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repositoryCourse: CourseRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {
    private val _isUserLogin = MutableLiveData<Boolean>()
    val isUserLogin: LiveData<Boolean>
        get() = _isUserLogin

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

    fun checkLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            val userStatus = userPreferenceDataSource.getUserToken().firstOrNull() != null
            _isUserLogin.postValue(userStatus)
        }
    }
}
