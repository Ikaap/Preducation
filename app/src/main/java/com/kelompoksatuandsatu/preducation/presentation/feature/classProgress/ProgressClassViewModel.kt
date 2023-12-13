package com.kelompoksatuandsatu.preducation.presentation.feature.classProgress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepository
import com.kelompoksatuandsatu.preducation.model.CategoryCourse
import com.kelompoksatuandsatu.preducation.model.CategoryPopular
import com.kelompoksatuandsatu.preducation.model.Course
import com.kelompoksatuandsatu.preducation.utils.AssetWrapper
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgressClassViewModel(
    val repositoryCourse: CourseRepository,
    private val assetsWrapper: AssetWrapper
) : ViewModel() {

    private val _courseProgress = MutableLiveData<ResultWrapper<List<Course>>>()
    val courseProgress: LiveData<ResultWrapper<List<Course>>>
        get() = _courseProgress

    fun getCourseProgress(category: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryCourse.getCourseUserProgress(if (category == assetsWrapper.getString(R.string.all))null else category?.lowercase())
                .collect {
                    _courseProgress.postValue(it)
                }
        }
    }

    private val _categoriesClass = MutableLiveData<ResultWrapper<List<CategoryCourse>>>()
    val categoriesClass: LiveData<ResultWrapper<List<CategoryCourse>>>
        get() = _categoriesClass

    fun getCategoriesClass() {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryCourse.getCategoriesClass().collect {
                _categoriesClass.postValue(it)
            }
        }
    }

    private val _categoriesProgress = MutableLiveData<ResultWrapper<List<CategoryPopular>>>()
    val categoriesProgress: LiveData<ResultWrapper<List<CategoryPopular>>>
        get() = _categoriesProgress

    fun getCategoriesProgress() {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryCourse.getCategoriesProgress().collect {
                _categoriesProgress.postValue(it)
            }
        }
    }
}
