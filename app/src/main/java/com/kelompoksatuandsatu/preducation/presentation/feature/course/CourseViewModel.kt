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

    private val _categories = MutableLiveData<ResultWrapper<List<CategoryClass>>>()
    val categories: LiveData<ResultWrapper<List<CategoryClass>>>
        get() = _categories

    fun getCourse(category: List<String>? = null, typeClass: String? = null, title: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryCourse.getCourseHomeFilter(category = category, typeClass = if (typeClass == "All") null else typeClass?.toLowerCase(), title = title)
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

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryCourse.getCategoriesClass().collect {
                _categories.postValue(it)
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

    private val _selectedType = MutableLiveData<String>()
    val selectedType: LiveData<String>
        get() = _selectedType

    private val _selectedCategories = MutableLiveData<List<CategoryClass>>()
    val selectedCategories: LiveData<List<CategoryClass>?>
        get() = _selectedCategories

    fun setSelectedCategories(categories: List<CategoryClass>) {
        _selectedCategories.value = categories
    }
}
