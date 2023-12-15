package com.kelompoksatuandsatu.preducation.data.network.api.datasource

import com.kelompoksatuandsatu.preducation.data.network.api.model.notification.NotificationResponse
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService

interface NotificationDataSource {

    suspend fun getUserNotifications(): NotificationResponse
}
class NotificationApiDataSource(private val apiClient: PreducationService) :
    NotificationDataSource {

    override suspend fun getUserNotifications(): NotificationResponse {
        return apiClient.getUserNotification()
    }
}
