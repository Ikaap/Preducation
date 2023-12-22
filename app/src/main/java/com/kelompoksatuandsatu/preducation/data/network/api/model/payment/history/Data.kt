package com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history

import com.google.gson.annotations.SerializedName
data class Data(
    @SerializedName("courseId")
    val courseId: CourseId?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("isActive")
    val isActive: Boolean?,
    @SerializedName("paymentType")
    val paymentType: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalPrice")
    val totalPrice: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("userId")
    val userId: UserId?
)

fun Data.toCourseItem() = CourseItem(
    courseId = this.courseId,
    createdAt = this.createdAt.orEmpty(),
    id = this.id.orEmpty(),
    status = this.status.orEmpty(),
    isActive = this.isActive ?: false,
    paymentType = this.paymentType.orEmpty(),
    totalPrice = this.totalPrice ?: 0,
    updatedAt = this.updatedAt.orEmpty(),
    userId = this.userId
)

fun Collection<Data>.toCourseItemList() = this.map { it.toCourseItem() }
