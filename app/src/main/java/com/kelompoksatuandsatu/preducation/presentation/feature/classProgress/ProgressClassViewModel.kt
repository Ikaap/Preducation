package com.kelompoksatuandsatu.preducation.presentation.feature.classProgress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepository
import com.kelompoksatuandsatu.preducation.model.category.categoryclass.CategoryClass
import com.kelompoksatuandsatu.preducation.model.category.categoryprogress.CategoryType
import com.kelompoksatuandsatu.preducation.model.progress.CourseProgressItemClass
import com.kelompoksatuandsatu.preducation.utils.AssetWrapper
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgressClassViewModel(
    private val repositoryCourse: CourseRepository,
    private val assetsWrapper: AssetWrapper,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {

    private val _courseProgress = MutableLiveData<ResultWrapper<List<CourseProgressItemClass>>>()
    val courseProgress: LiveData<ResultWrapper<List<CourseProgressItemClass>>>
        get() = _courseProgress

    fun getCourseProgress(category: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryCourse.getCourseUserProgress(if (category == assetsWrapper.getString(R.string.all)) null else category?.lowercase())
                .collect {
                    _courseProgress.postValue(it)
                }
        }
    }

    private val _categoriesClass = MutableLiveData<ResultWrapper<List<CategoryClass>>>()
    val categoriesClass: LiveData<ResultWrapper<List<CategoryClass>>>
        get() = _categoriesClass

    fun getCategoriesClass() {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryCourse.getCategoriesClass().collect {
                _categoriesClass.postValue(it)
            }
        }
    }

    private val _categoriesProgress = MutableLiveData<ResultWrapper<List<CategoryType>>>()
    val categoriesProgress: LiveData<ResultWrapper<List<CategoryType>>>
        get() = _categoriesProgress

    fun getCategoriesProgress() {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryCourse.getCategoriesProgress().collect {
                _categoriesProgress.postValue(it)
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
