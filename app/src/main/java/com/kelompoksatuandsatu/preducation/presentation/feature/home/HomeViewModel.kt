package com.kelompoksatuandsatu.preducation.presentation.feature.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepository
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.model.category.categoryclass.CategoryClass
import com.kelompoksatuandsatu.preducation.model.course.courseall.CourseViewParam
import com.kelompoksatuandsatu.preducation.model.user.UserViewParam
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val courseRepo: CourseRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource,
    private val userRepo: UserRepository
) : ViewModel() {

    private val _categoriesClass = MutableLiveData<ResultWrapper<List<CategoryClass>>>()
    val categoriesClass: LiveData<ResultWrapper<List<CategoryClass>>>
        get() = _categoriesClass

    private val _categoriesClassPopular = MutableLiveData<ResultWrapper<List<CategoryClass>>>()
    val categoriesClassPopular: LiveData<ResultWrapper<List<CategoryClass>>>
        get() = _categoriesClassPopular

    private val _coursePopular = MutableLiveData<ResultWrapper<List<CourseViewParam>>>()
    val coursePopular: LiveData<ResultWrapper<List<CourseViewParam>>>
        get() = _coursePopular

    private val _isUserLogin = MutableLiveData<Boolean>()
    val isUserLogin: LiveData<Boolean>
        get() = _isUserLogin

    private val _getProfile = MutableLiveData<ResultWrapper<UserViewParam>>()
    val getProfile: LiveData<ResultWrapper<UserViewParam>>
        get() = _getProfile

    private val _isFilterEmpty = MutableLiveData<Boolean>()
    val isFilterEmpty: LiveData<Boolean>
        get() = _isFilterEmpty

    fun getUserById() {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = userPreferenceDataSource.getUserId()
            Log.d("USER ID PROFILE", "getUserById: $userId")
            userRepo.getUserById(userId).collect {
                _getProfile.postValue(it)
            }
        }
    }

    fun checkLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            val userStatus = userPreferenceDataSource.getUserToken().firstOrNull() != null
            _isUserLogin.postValue(userStatus)
        }
    }

    fun getCategoriesClass() {
        viewModelScope.launch(Dispatchers.IO) {
            courseRepo.getCategoriesClass().collect {
                _categoriesClass.postValue(it)
            }
        }
    }

    fun getCategoriesClassPopular() {
        viewModelScope.launch(Dispatchers.IO) {
            courseRepo.getCategoriesClass().collect {
                _categoriesClassPopular.postValue(it)
            }
        }
    }

    fun getCourse(category: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            courseRepo.getCourseHome(if (category == "All") null else category?.toLowerCase())
                .collect {
                    _coursePopular.postValue(it)
                }
        }
    }
}
