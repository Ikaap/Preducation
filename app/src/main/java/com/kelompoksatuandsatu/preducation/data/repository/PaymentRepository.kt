package com.kelompoksatuandsatu.preducation.data.repository

import com.kelompoksatuandsatu.preducation.data.network.api.datasource.PaymentDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history.HistoryPaymentResponse
import kotlinx.coroutines.flow.Flow

class PaymentRepository(private val paymentDataSource: PaymentDataSource) {

    val payments: Flow<HistoryPaymentResponse> = paymentDataSource.payments

    suspend fun fetchPayments() {
        paymentDataSource.fetchPayments()
    }
}
