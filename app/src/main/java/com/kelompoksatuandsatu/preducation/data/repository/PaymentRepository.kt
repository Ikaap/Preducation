package com.kelompoksatuandsatu.preducation.data.repository

import com.kelompoksatuandsatu.preducation.data.network.api.datasource.PaymentDataSource

class PaymentRepository(private val dataSource: PaymentDataSource) {
    suspend fun getHistoryPayment(accessToken: String) = dataSource.getHistoryPayment(accessToken)
}
