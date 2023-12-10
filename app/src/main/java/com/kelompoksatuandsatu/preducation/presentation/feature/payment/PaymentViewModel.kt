package com.kelompoksatuandsatu.preducation.presentation.feature.payment

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepository
import com.kelompoksatuandsatu.preducation.model.PaymentResponseViewParam
import com.kelompoksatuandsatu.preducation.model.detailcourse.DetailCourseViewParam
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val extras: Bundle?,
    private val courseRepo: CourseRepository
) : ViewModel() {
    val course = extras?.getParcelable<DetailCourseViewParam>(PaymentActivity.EXTRA_DETAIL_COURSE)

    private val _paymentResult = MutableLiveData<ResultWrapper<PaymentResponseViewParam>>()
    val paymentResult: LiveData<ResultWrapper<PaymentResponseViewParam>>
        get() = _paymentResult

    fun payment() {
        viewModelScope.launch(Dispatchers.IO) {
            val request = course
            Log.d("MyTag", "Request: $request")
            if (request != null) {
                courseRepo.paymentCourse(request).collect {
                    _paymentResult.postValue(it)
                }
            }
//            request?.let {
//                courseRepo.paymentCourse(it).collect { result ->
//                    Log.d("MyTag", "Result: $result")
//                    _paymentResult.postValue(result)
//                }
//            }
        }
    }
}
