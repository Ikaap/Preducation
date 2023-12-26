package com.kelompoksatuandsatu.preducation.data.network.api.datasource

import com.kelompoksatuandsatu.preducation.data.network.api.model.notification.NotificationResponse
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NotificationApiDataSourceTest {

    @MockK
    lateinit var service: PreducationService

    private lateinit var notificationDataSource: NotificationDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        notificationDataSource = NotificationApiDataSource(service)
    }

    @Test
    fun getUserNotifications() {
        runTest {
            val mockResponse = mockk<NotificationResponse>(relaxed = true)
            coEvery { service.getUserNotification() } returns mockResponse
            val response = notificationDataSource.getUserNotifications()
            coVerify { service.getUserNotification() }
            assertEquals(response, mockResponse)
        }
    }
}
