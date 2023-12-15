package com.kelompoksatuandsatu.preducation.data.network.api.model.notification

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.kelompoksatuandsatu.preducation.model.NotificationItem

@Keep
data class Data(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("isActive")
    val isActive: Boolean?,
    @SerializedName("isRead")
    val isRead: Boolean?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("userId")
    val userId: UserId?,
    @SerializedName("__v")
    val v: Int?
)

fun Data.toNotificationItem() = NotificationItem(
    createdAt = this.createdAt.orEmpty(),
    description = this.description.orEmpty(),
    id = this.id.orEmpty(),
    isActive = this.isActive ?: false,
    isRead = this.isRead ?: false,
    title = this.title.orEmpty(),
    userId = this.userId,
    v = this.v ?: 0

)

fun Collection<Data>.toNotificationItemList() = this.map { it.toNotificationItem() }
