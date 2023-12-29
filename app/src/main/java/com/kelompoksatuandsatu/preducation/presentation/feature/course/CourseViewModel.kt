package com.kelompoksatuandsatu.preducation.presentation.feature.course

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepository
import com.kelompoksatuandsatu.preducation.model.category.categoryclass.CategoryClass
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

    private val _categoriesClass = MutableLiveData<ResultWrapper<List<CategoryClass>>>()
    val categoriesClass: LiveData<ResultWrapper<List<CategoryClass>>>
        get() = _categoriesClass

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String>
        get() = _searchQuery

    private val _categoriesTypeClass = MutableLiveData<ResultWrapper<List<CategoryType>>>()
    val categoriesTypeClass: LiveData<ResultWrapper<List<CategoryType>>>
        get() = _categoriesTypeClass

    private val _selectedType = MutableLiveData<String>()
    val selectedType: LiveData<String>
        get() = _selectedType

    private val _selectedCategories = MutableLiveData<List<CategoryClass>>()
    val selectedCategories: LiveData<List<CategoryClass>?>
        get() = _selectedCategories

    fun getCourse(category: List<String>? = null, typeClass: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryCourse.getCourseHome(category = category, if (typeClass == "All") null else typeClass?.toLowerCase())
                .collect {
                    _course.postValue(it)
                }
        }
    }

    fun addSelectedCategory(category: CategoryClass) {
        val updatedData = _selectedCategories.value.orEmpty().toMutableList()
        updatedData.add(category)
        _selectedCategories.value = updatedData.distinct()
    }

    fun deleteSelectedCategory(category: CategoryClass) {
        val updatedData = _selectedCategories.value.orEmpty().toMutableList()
        updatedData.remove(category)
        _selectedCategories.value = updatedData.distinct()
    }

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

    fun getCategoriesClass() {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryCourse.getCategoriesClass().collect {
                _categoriesClass.postValue(it)
            }
        }
    }
}
