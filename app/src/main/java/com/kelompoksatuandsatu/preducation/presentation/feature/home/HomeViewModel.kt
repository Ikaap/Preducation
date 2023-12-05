package com.kelompoksatuandsatu.preducation.presentation.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepository
import com.kelompoksatuandsatu.preducation.model.CategoryClass
import com.kelompoksatuandsatu.preducation.model.Course
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val courseRepo: CourseRepository
) : ViewModel() {

    private val _categoriesClass = MutableLiveData<ResultWrapper<List<CategoryClass>>>()
    val categoriesClass: LiveData<ResultWrapper<List<CategoryClass>>>
        get() = _categoriesClass

    private val _categoriesClassPopular = MutableLiveData<ResultWrapper<List<CategoryClass>>>()
    val categoriesClassPopular: LiveData<ResultWrapper<List<CategoryClass>>>
        get() = _categoriesClassPopular

    private val _course = MutableLiveData<ResultWrapper<List<Course>>>()
    val course: LiveData<ResultWrapper<List<Course>>>
        get() = _course

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
            courseRepo.getCourseHome(if (category == "All") null else category?.lowercase()).collect {
                _course.postValue(it)
            }
        }
    }
}
