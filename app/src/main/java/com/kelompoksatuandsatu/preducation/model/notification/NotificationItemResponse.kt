package com.kelompoksatuandsatu.preducation.model.notification

import com.google.errorprone.annotations.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class NotificationItemResponse(
    @SerializedName("id")
    val _id: Int,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String
)

fun NotificationItemResponse.toNotificationItem() = NotificationItem(
    _id = this._id,
    userId = this.userId,
    title = this.title,
    description = this.description
)

fun Collection<NotificationItemResponse>.toNotificationItemList() = this.map { it.toNotificationItem() }
