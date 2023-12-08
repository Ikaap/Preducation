package com.kelompoksatuandsatu.preducation.data.network.api.datasource

import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService
import com.kelompoksatuandsatu.preducation.model.notification.NotificationResponse

interface NotificationDataSource {

    suspend fun getUserNotifications(): NotificationResponse
}
class NotificationApiDataSource(private val apiClient: PreducationService) :
    NotificationDataSource {

    override suspend fun getUserNotifications(): NotificationResponse {
        return apiClient.getUserNotifications()
    }
}

//    suspend fun markNotificationAsRead(@Path("id") id: String?): NotificationResponse
//
//    suspend fun createNotificationForAllUsers(
//        @Body notification: NotificationItem,
//        @Query("title") title: String,
//        @Query("description") description: String,
//    ): NotificationResponse
//
//    suspend fun createNotificationForSpecificUser(
//        @Body notification: NotificationItem,
//        @Query("title") title: String,
//        @Query("description") description: String,
//        @Query("userId") userId: Int,
//    ): NotificationResponse
// }

//    override suspend fun markNotificationAsRead(id: String?): NotificationResponse {
//        return apiClient.markNotificationAsRead(id)
//    }
//
//    override suspend fun createNotificationForAllUsers(
//        @Body notification: NotificationItem,
//        title: String,
//        description: String
//    ): NotificationResponse {
//        return apiClient.createNotificationForAllUsers(title, description)
//    }
//
//    override suspend fun createNotificationForSpecificUser(
//        @Body notification: NotificationItem,
//        title: String,
//        description: String,
//        userId: Int
//    ): NotificationResponse {
//        return apiClient.createNotificationForSpecificUser(title, description, 1)
//    }
// }
