package com.kelompoksatuandsatu.preducation.presentation.feature.course

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepository
import com.kelompoksatuandsatu.preducation.model.CategoryPopular
import com.kelompoksatuandsatu.preducation.model.CourseViewParam
import com.kelompoksatuandsatu.preducation.utils.AssetWrapper
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CourseViewModel(
    val repositoryCourse: CourseRepository,
    private val assetsWrapper: AssetWrapper
): ViewModel() {

    private val _course = MutableLiveData<ResultWrapper<List<CourseViewParam>>>()
    val course: LiveData<ResultWrapper<List<CourseViewParam>>>
        get() = _course

    fun getCourse(category: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryCourse.getCourseHome(if (category == assetsWrapper.getString(R.string.all))null else category?.lowercase())
                .collect {
                    _course.postValue(it)
                }
        }
    }

    private val _categoriesTypeClass = MutableLiveData<ResultWrapper<List<CategoryPopular>>>()
    val categoriesTypeClass: LiveData<ResultWrapper<List<CategoryPopular>>>
        get() = _categoriesTypeClass

    fun getCategoriesTypeClass() {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryCourse.getCategoriesTypeClass().collect {
                _categoriesTypeClass.postValue(it)
            }
        }
    }
}