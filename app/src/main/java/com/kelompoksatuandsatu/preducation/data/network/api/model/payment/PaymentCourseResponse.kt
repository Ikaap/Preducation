package com.kelompoksatuandsatu.preducation.data.network.api.model.payment

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.kelompoksatuandsatu.preducation.model.payment.PaymentResponseViewParam

@Keep
data class PaymentCourseResponse(
    @SerializedName("data")
    val data: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)

fun PaymentCourseResponse.toPaymentResponse() = PaymentResponseViewParam(
    data = this.data,
    message = this.message,
    success = this.success
)
