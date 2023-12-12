package com.kelompoksatuandsatu.preducation.data.network.api.model.historypayment

import com.google.gson.annotations.SerializedName
import com.kelompoksatuandsatu.preducation.model.Course
import com.kelompoksatuandsatu.preducation.model.User

data class HistoryPaymentItemResponse(
    @SerializedName("_id")
    val id: String,
    @SerializedName("courseId")
    val courseId: Course,
    @SerializedName("userId")
    val userId: User,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalPrice")
    val totalPrice: Int,
    @SerializedName("isActive")
    val isActive: Boolean,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("paymentType")
    val paymentType: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)
