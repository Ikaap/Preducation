package com.kelompoksatuandsatu.preducation.data.repository

import com.kelompoksatuandsatu.preducation.data.network.api.datasource.NotificationDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.notification.toNotificationItemList
import com.kelompoksatuandsatu.preducation.model.notification.NotificationItem
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import com.kelompoksatuandsatu.preducation.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface NotificationRepository {
    suspend fun getUserNotifications(): Flow<ResultWrapper<List<NotificationItem>>>
}

class NotificationRepositoryImpl(private val notificationDataSource: NotificationDataSource) :
    NotificationRepository {

    override suspend fun getUserNotifications(): Flow<ResultWrapper<List<NotificationItem>>> {
        return proceedFlow {
            notificationDataSource.getUserNotifications().data?.toNotificationItemList()
                ?: emptyList()
        }.map {
            if (it.payload?.isEmpty() == true) {
                ResultWrapper.Empty(it.payload)
            } else {
                it
            }
        }.catch {
            emit(ResultWrapper.Error(Exception(it)))
        }.onStart {
            emit(ResultWrapper.Loading())
            delay(3000)
        }
    }
}
