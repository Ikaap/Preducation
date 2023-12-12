package com.kelompoksatuandsatu.preducation.presentation.feature.detailclass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepository
import com.kelompoksatuandsatu.preducation.model.detailcourse.DetailCourseViewParam
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailClassViewModel(
    private val courseRepo: CourseRepository
) : ViewModel() {
    private val _detailCourse = MutableLiveData<ResultWrapper<DetailCourseViewParam>>()
    val detailCourse: LiveData<ResultWrapper<DetailCourseViewParam>>
        get() = _detailCourse

    private val _progressVideo = MutableLiveData<ResultWrapper<Boolean>>()
    val progressVideo: LiveData<ResultWrapper<Boolean>>
        get() = _progressVideo

    fun getCourseById(courseId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            courseRepo.getCourseById(courseId).collect {
                _detailCourse.postValue(it)
            }
        }
    }

    fun postIndexVideo(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = detailCourse.value?.payload?.id
            id?.let {
                courseRepo.postIndexCourseById(it, index).collect {
                    _progressVideo.postValue(it)
                }
            }
        }
    }
}
