package com.kelompoksatuandsatu.preducation.presentation.feature.detailclass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepository
import com.kelompoksatuandsatu.preducation.model.DetailCourseViewParam
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailClassViewModel(
    private val courseRepo: CourseRepository
) : ViewModel() {
//    private val courseList = courseRepo.getCourseHome().asLiveData(Dispatchers.IO)

    private val _detailCourse = MutableLiveData<ResultWrapper<DetailCourseViewParam>>()
    val detailCourse: LiveData<ResultWrapper<DetailCourseViewParam>>
        get() = _detailCourse

    fun getCourseById(courseId: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
//            val courseId = courseList.value?.payload?.first()?.id
            courseRepo.getCourseById(courseId).collect {
                _detailCourse.postValue(it)
            }
        }
    }
}
