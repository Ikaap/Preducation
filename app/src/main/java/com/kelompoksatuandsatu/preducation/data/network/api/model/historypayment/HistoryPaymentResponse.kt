package com.kelompoksatuandsatu.preducation.data.network.api.model.historypayment

import com.google.gson.annotations.SerializedName

data class HistoryPaymentResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<HistoryPaymentItemResponse>
)
