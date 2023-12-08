package com.kelompoksatuandsatu.preducation.data.repository

import com.kelompoksatuandsatu.preducation.data.network.api.datasource.NotificationDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.notification.toNotificationItemList
import com.kelompoksatuandsatu.preducation.model.NotificationItem
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
