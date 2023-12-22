package com.kelompoksatuandsatu.preducation.model.notification

import com.kelompoksatuandsatu.preducation.data.network.api.model.notification.UserId

data class NotificationItem(
    val createdAt: String?,
    val description: String?,
    val id: String?,
    val isActive: Boolean?,
    val isRead: Boolean?,
    val title: String?,
    val userId: UserId?,
    val v: Int?
)
