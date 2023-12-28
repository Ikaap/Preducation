package com.kelompoksatuandsatu.preducation.data.repository

import app.cash.turbine.test
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.NotificationDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.notification.Data
import com.kelompoksatuandsatu.preducation.data.network.api.model.notification.NotificationResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.notification.UserId
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NotificationRepositoryImplTest {

    @MockK
    lateinit var notifDataSource: NotificationDataSource

    private lateinit var notifRepo: NotificationRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        notifRepo = NotificationRepositoryImpl(notifDataSource)
    }

    @Test
    fun `user notification, result loading`() {
        val mockNotificationResponse = mockk<NotificationResponse>()
        runTest {
            coEvery { notifDataSource.getUserNotifications() } returns mockNotificationResponse
            notifRepo.getUserNotifications().map {
                delay(100)
                it
            }.test {
                delay(3120)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Loading)
            }
        }
    }

    @Test
    fun `user notification, result success`() {
        val fakeUserId = UserId(
            id = "p1",
            city = "Cilacap",
            role = "User",
            country = "Indoensia",
            createdAt = "Cilacap",
            email = "email",
            username = "username",
            imageProfile = "url",
            isActive = true,
            isVerify = true,
            name = "ikap",
            phone = "0800000000"
        )
        val fakeNotificationItemResponse = Data(
            createdAt = "Cilacap",
            description = "desc",
            id = "notif1",
            isActive = false,
            title = "Login Success",
            isRead = true,
            userId = fakeUserId,
            v = 1
        )
        val fakeNotificationResponse = NotificationResponse(
            data = listOf(fakeNotificationItemResponse),
            message = "success",
            success = true
        )
        runTest {
            coEvery { notifDataSource.getUserNotifications() } returns fakeNotificationResponse
            notifRepo.getUserNotifications().map {
                delay(100)
                it
            }.test {
                delay(3320)
                val result = expectMostRecentItem()
                assertEquals(result.payload?.size, 1)
                assertEquals(result.payload?.get(0)?.title, "Login Success")
                assertTrue(result is ResultWrapper.Success)
                coVerify { notifDataSource.getUserNotifications() }
            }
        }
    }

    @Test
    fun `user notification, result empty`() {
        val fakeNotificationResponse = NotificationResponse(
            data = emptyList(),
            message = "success",
            success = true
        )
        runTest {
            coEvery { notifDataSource.getUserNotifications() } returns fakeNotificationResponse
            notifRepo.getUserNotifications().map {
                delay(100)
                it
            }.test {
                delay(3320)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Empty)
                coVerify { notifDataSource.getUserNotifications() }
            }
        }
    }

    @Test
    fun `user notification, result error`() {
        runTest {
            coEvery { notifDataSource.getUserNotifications() } throws IllegalStateException("Error")
            notifRepo.getUserNotifications().map {
                delay(100)
                it
            }.test {
                delay(3320)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Error)
                coVerify { notifDataSource.getUserNotifications() }
            }
        }
    }
}
