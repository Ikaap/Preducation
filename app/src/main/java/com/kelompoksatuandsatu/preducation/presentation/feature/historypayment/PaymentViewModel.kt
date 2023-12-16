package com.kelompoksatuandsatu.preducation.presentation.feature.historypayment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history.Payment
import com.kelompoksatuandsatu.preducation.data.repository.PaymentRepository
import kotlinx.coroutines.launch

class PaymentViewModel(private val repository: PaymentRepository) : ViewModel() {
    private val _payments = MutableLiveData<List<Payment>>()
    val payments: LiveData<List<Payment>> get() = _payments

    fun fetchPayments(accessToken: String) {
        viewModelScope.launch {
            val response = repository.getHistoryPayment(accessToken)
            if (response.success) {
                _payments.value = response.data
            } else {
                // handle error
            }
        }
    }
}
