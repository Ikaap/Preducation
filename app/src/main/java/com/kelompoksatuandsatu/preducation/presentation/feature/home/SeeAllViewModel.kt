package com.kelompoksatuandsatu.preducation.presentation.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepository
import com.kelompoksatuandsatu.preducation.model.category.categoryclass.CategoryClass
import com.kelompoksatuandsatu.preducation.model.course.courseall.CourseViewParam
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SeeAllViewModel(
    private val courseRepo: CourseRepository
) : ViewModel() {
    private val _categoriesClassPopular = MutableLiveData<ResultWrapper<List<CategoryClass>>>()
    val categoriesClassPopular: LiveData<ResultWrapper<List<CategoryClass>>>
        get() = _categoriesClassPopular

    private val _coursePopular = MutableLiveData<ResultWrapper<List<CourseViewParam>>>()
    val coursePopular: LiveData<ResultWrapper<List<CourseViewParam>>>
        get() = _coursePopular

    fun getCategoriesClassPopular() {
        viewModelScope.launch(Dispatchers.IO) {
            courseRepo.getCategoriesClass().collect {
                _categoriesClassPopular.postValue(it)
            }
        }
    }

    fun getCourse(category: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            courseRepo.getCourseHome(if (category == "All") null else category?.lowercase()).collect {
                _coursePopular.postValue(it)
            }
        }
    }
}
