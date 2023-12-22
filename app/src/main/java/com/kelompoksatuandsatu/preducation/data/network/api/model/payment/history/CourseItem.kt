package com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history

data class CourseItem(
    val courseId: CourseId?,
    val createdAt: String?,
    val id: String?,
    val isActive: Boolean?,
    val paymentType: String?,
    val status: String?,
    val totalPrice: Int?,
    val updatedAt: String?,
    val userId: UserId?
)
