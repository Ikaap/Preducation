package com.kelompoksatuandsatu.preducation.data.network.api.datasource

import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history.HistoryPaymentResponse
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PaymentDataSource(private val preducationService: PreducationService) {

    val payments: Flow<HistoryPaymentResponse> = flow {
        emit(preducationService.getHistoryPayment())
    }

    suspend fun fetchPayments() {
        preducationService.getHistoryPayment()
    }
}
