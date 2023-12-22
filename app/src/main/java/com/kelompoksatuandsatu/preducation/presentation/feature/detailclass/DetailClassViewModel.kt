package com.kelompoksatuandsatu.preducation.presentation.feature.detailclass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepository
import com.kelompoksatuandsatu.preducation.model.course.detailcourse.DetailCourseViewParam
import com.kelompoksatuandsatu.preducation.model.course.detailcourse.VideoViewParam
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

//    val progressVideo: LiveData<ResultWrapper<Boolean>>
//        get() = _progressVideo

    private val _selectedVideoId = MutableLiveData<String>()
    val selectedVideoId: LiveData<String> get() = _selectedVideoId

    fun getCourseById(courseId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            courseRepo.getCourseById(courseId).collect {
                _detailCourse.postValue(it)
            }
        }
    }

//    fun getCourseById() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val id = detailCourse.value?.payload?.id
//            id?.let {
//                courseRepo.getCourseById(it).collect {
//                    _detailCourse.postValue(it)
//                }
//            }
//        }
//    }

    fun postIndexVideo(index: VideoViewParam) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = detailCourse.value?.payload?.id
            id?.let {
                courseRepo.postIndexCourseById(it, index).collect {
                    _progressVideo.postValue(it)
                }
            }
        }
    }

    fun onVideoItemClick(videoId: String) {
        _selectedVideoId.postValue(videoId)
    }

    private val _selectedVideoIndex = MutableLiveData<Int>()
    val selectedVideoIndex: LiveData<Int>
        get() = _selectedVideoIndex

    fun getNextVideoId(): String? {
        val listVideo = detailCourse.value?.payload?.chapters?.flatMap { it.videos ?: emptyList() }
        val video = listVideo?.map { it.videoUrl.orEmpty() } ?: emptyList()

        val nextIndex = selectedVideoIndex.value?.plus(1) ?: 1

        return if (nextIndex < video.size) {
            _selectedVideoIndex.postValue(nextIndex)
            video[nextIndex]
        } else {
            null
        }
    }
}
