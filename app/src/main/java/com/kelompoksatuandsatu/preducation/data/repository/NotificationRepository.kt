package com.kelompoksatuandsatu.preducation.data.repository

import com.kelompoksatuandsatu.preducation.data.network.api.datasource.NotificationDataSource
import com.kelompoksatuandsatu.preducation.model.notification.NotificationItem
import com.kelompoksatuandsatu.preducation.model.notification.toNotificationItemList
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import com.kelompoksatuandsatu.preducation.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun getUserNotifications(): Flow<ResultWrapper<List<NotificationItem>>>
}

class NotificationRepositoryImpl(private val notificationDataSource: NotificationDataSource) :
    NotificationRepository {

    override suspend fun getUserNotifications(): Flow<ResultWrapper<List<NotificationItem>>> {
        return proceedFlow {
            notificationDataSource.getUserNotifications().data?.toNotificationItemList() ?: emptyList()
        }
    }
}

//    suspend fun markNotificationAsRead(id: String?): Flow<ResultWrapper<List<NotificationItem>>>
//    suspend fun createNotificationForAllUsers(
//        notification: NotificationItem,
//        title: String,
//        description: String
//    ): Flow<ResultWrapper<List<NotificationItem>>>
//
//    suspend fun createNotificationForSpecificUser(
//        notification: NotificationItem,
//        title: String,
//        description: String,
//        userId: Int
//    ): Flow<ResultWrapper<List<NotificationItem>>>
