package com.kelompoksatuandsatu.preducation.data.network.api.datasource

import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService

class PaymentDataSource(private val preducationService: PreducationService) {
    suspend fun getHistoryPayment(accessToken: String) = preducationService.getHistoryPayment(accessToken)
}
