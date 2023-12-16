package com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history

import com.google.gson.annotations.SerializedName

data class HistoryPaymentRequest(
    @SerializedName("accessToken")
    val accessToken: String
)
