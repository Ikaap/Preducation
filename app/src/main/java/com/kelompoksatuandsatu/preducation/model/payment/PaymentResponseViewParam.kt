package com.kelompoksatuandsatu.preducation.model.payment

import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.Data

// data class PaymentResponseViewParam(
//    val redirectUrl: String?,
//    val token: String?
// )

data class PaymentResponseViewParam(
    val data: Data?,
    val message: String?,
    val success: Boolean?
)
