package com.kelompoksatuandsatu.preducation.data.network.api.model.payment

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PaymentCourseRequest(
    @SerializedName("courseId")
    val courseId: String?,
    @SerializedName("courseTitle")
    val courseTitle: String?,
    @SerializedName("totalPrice")
    val totalPrice: Int?
)
