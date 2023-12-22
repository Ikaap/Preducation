package com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class HistoryItemResponse(
    @SerializedName("data")
    val data: List<Data>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)
