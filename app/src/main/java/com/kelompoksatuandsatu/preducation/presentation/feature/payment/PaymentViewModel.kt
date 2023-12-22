package com.kelompoksatuandsatu.preducation.presentation.feature.payment

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepository
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.model.auth.otp.postemailotp.EmailOtp
import com.kelompoksatuandsatu.preducation.model.course.detailcourse.DetailCourseViewParam
import com.kelompoksatuandsatu.preducation.model.payment.PaymentResponseViewParam
import com.kelompoksatuandsatu.preducation.model.user.UserViewParam
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val extras: Bundle?,
    private val courseRepo: CourseRepository,
    private val userRepo: UserRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {
    val course = extras?.getParcelable<DetailCourseViewParam>(PaymentActivity.EXTRA_DETAIL_COURSE)

    private val _paymentResult = MutableLiveData<ResultWrapper<PaymentResponseViewParam>>()
    val paymentResult: LiveData<ResultWrapper<PaymentResponseViewParam>>
        get() = _paymentResult

    private val _emailOtpResult = MutableLiveData<ResultWrapper<Boolean>>()
    val emailOtpResult: LiveData<ResultWrapper<Boolean>>
        get() = _emailOtpResult

    fun payment() {
        viewModelScope.launch(Dispatchers.IO) {
            val request = course
            Log.d("DATA PAYMENT", "Request: ${request?.id}, ${request?.title}, ${request?.price}, ")
            if (request != null) {
                courseRepo.paymentCourse(request).collect {
                    _paymentResult.postValue(it)
                }
            }
        }
    }

    private val _getUserData = MutableLiveData<ResultWrapper<UserViewParam>>()
    val getUserData: LiveData<ResultWrapper<UserViewParam>>
        get() = _getUserData
    fun getUserById() {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = userPreferenceDataSource.getUserId()
            Log.d("USER ID PAYMENT", "getUserById: $userId")
            userRepo.getUserById(userId).collect {
                _getUserData.postValue(it)
            }
        }
    }
    fun postEmailOtp(request: EmailOtp) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.postEmailOtp(request).collect {
                _emailOtpResult.postValue(it)
            }
        }
    }

    private val _detailCourse = MutableLiveData<ResultWrapper<DetailCourseViewParam>>()
    val detailCourse: LiveData<ResultWrapper<DetailCourseViewParam>>
        get() = _detailCourse

    fun getCourseById() {
        viewModelScope.launch(Dispatchers.IO) {
            val courseId = course?.id
            courseId?.let { id ->
                courseRepo.getCourseById(id).collect {
                    _detailCourse.postValue(it)
                }
            }
        }
    }
}
