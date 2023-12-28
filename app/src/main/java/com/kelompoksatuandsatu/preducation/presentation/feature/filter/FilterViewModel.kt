package com.kelompoksatuandsatu.preducation.presentation.feature.filter

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

class FilterViewModel(
    private val courseRepo: CourseRepository
) : ViewModel() {
    private val _categoriesClass = MutableLiveData<ResultWrapper<List<CategoryClass>>>()
    val categoriesClass: LiveData<ResultWrapper<List<CategoryClass>>>
        get() = _categoriesClass

    private val _coursePopular = MutableLiveData<ResultWrapper<List<CourseViewParam>>>()
    val coursePopular: LiveData<ResultWrapper<List<CourseViewParam>>>
        get() = _coursePopular

    fun getCategoriesClass() {
        viewModelScope.launch(Dispatchers.IO) {
            courseRepo.getCategoriesClass().collect {
                _categoriesClass.postValue(it)
            }
        }
    }

    fun getCourse(category: String? = null, typeClass: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            courseRepo.getCourseHome(if (category == "All") null else category?.toLowerCase(), typeClass)
                .collect {
                    _coursePopular.postValue(it)
                }
        }
    }

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String>
        get() = _searchQuery

    private val _selectedType = MutableLiveData<String>()
    val selectedType: LiveData<String>
        get() = _selectedType

    private val _selectedCategories = MutableLiveData<List<Int>>()
    val selectedCategories: LiveData<List<Int>?>
        get() = _selectedCategories

    fun isSelectedCategory(category: CategoryClass): Boolean {
        return category.isSelected
    }
}
