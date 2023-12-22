package com.kelompoksatuandsatu.preducation.presentation.feature.historypayment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history.CourseItem
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepository
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryPaymentViewModel(private val paymentRepository: CourseRepository) : ViewModel() {

    private val _payment = MutableLiveData<ResultWrapper<List<CourseItem>>>()
    val payment: LiveData<ResultWrapper<List<CourseItem>>>
        get() = _payment

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            paymentRepository.getHistoryPayment().collect {
                _payment.postValue(it)
            }
        }
    }
}
