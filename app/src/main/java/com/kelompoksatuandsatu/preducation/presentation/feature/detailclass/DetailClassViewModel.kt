package com.kelompoksatuandsatu.preducation.presentation.feature.detailclass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepository
import com.kelompoksatuandsatu.preducation.model.detailcourse.DetailCourseViewParam
import com.kelompoksatuandsatu.preducation.model.detailcourse.VideoViewParam
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailClassViewModel(
    private val courseRepo: CourseRepository
) : ViewModel() {
//    private val courseList = courseRepo.getCourseHome().asLiveData(Dispatchers.IO)

    private val _detailCourse = MutableLiveData<ResultWrapper<DetailCourseViewParam>>()
    val detailCourse: LiveData<ResultWrapper<DetailCourseViewParam>>
        get() = _detailCourse

    private val _progressVideo = MutableLiveData<ResultWrapper<Boolean>>()
    val progressVideo: LiveData<ResultWrapper<Boolean>>
        get() = _progressVideo

    fun getCourseById(courseId: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
//            val courseId = courseList.value?.payload?.first()?.id
            courseRepo.getCourseById(courseId).collect {
                _detailCourse.postValue(it)
            }
        }
    }

//    fun postIndexCourseById(){
//        viewModelScope.launch(Dispatchers.IO){
//            val id = detailCourse.value?.payload?.id
//            val req = detailCourse.value?.payload?.chapters?.get(0)?.videos?.get(0)?.index
//            if (req != null) {
//                courseRepo.postIndexCourseById(id, req).collect{
//                    _progressVideo.postValue(it)
//                }
//            }
//        }
//    }

    fun postIndexVideo(index: VideoViewParam) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = detailCourse.value?.payload?.id
            courseRepo.postIndexCourseById(id, index).collect {
                _progressVideo.postValue(it)
            }
        }
    }
}
