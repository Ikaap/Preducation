package com.kelompoksatuandsatu.preducation.model.notification

import com.google.errorprone.annotations.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class NotificationResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: List<NotificationItemResponse>?,
    @SerializedName("status")
    val status: String? = null
)
