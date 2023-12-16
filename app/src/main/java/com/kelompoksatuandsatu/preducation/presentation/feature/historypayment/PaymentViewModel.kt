package com.kelompoksatuandsatu.preducation.presentation.feature.historypayment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history.HistoryPaymentResponse
import com.kelompoksatuandsatu.preducation.data.repository.PaymentRepository
import kotlinx.coroutines.launch

class PaymentViewModel(private val paymentRepository: PaymentRepository) : ViewModel() {

    val payments: LiveData<HistoryPaymentResponse> = paymentRepository.payments.asLiveData()

    fun fetchPayments() {
        viewModelScope.launch {
            paymentRepository.fetchPayments()
        }
    }
}
