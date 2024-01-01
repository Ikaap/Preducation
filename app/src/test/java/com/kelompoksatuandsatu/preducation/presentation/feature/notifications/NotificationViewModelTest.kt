package com.kelompoksatuandsatu.preducation.presentation.feature.notifications

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.notification.UserId
import com.kelompoksatuandsatu.preducation.data.repository.NotificationRepository
import com.kelompoksatuandsatu.preducation.model.notification.NotificationItem
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import com.tools.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class NotificationViewModelTest {
    @MockK
    private lateinit var notifRepo: NotificationRepository

    @MockK
    private lateinit var userPreferenceDataSource: UserPreferenceDataSource

    private lateinit var viewModel: NotificationViewModel

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = spyk(NotificationViewModel(notifRepo, userPreferenceDataSource))

        val resultListNotification = flow {
            emit(ResultWrapper.Success(listOf(NotificationItem("Cilacap", "desc", "id", true, true, "title", UserId("city", "country", "Cilacap", "email", "id", "url", true, true, "name", "089", "user", "username"), 1))))
        }
        coEvery { notifRepo.getUserNotifications() } returns resultListNotification
        coEvery { userPreferenceDataSource.getUserToken() } returns ""
    }

    @Test
    fun `get categories class`() {
        viewModel.getData()
        coVerify { notifRepo.getUserNotifications() }
    }

    @Test
    fun `check login user`() {
        viewModel.checkLogin()
        coVerify { userPreferenceDataSource.getUserToken() }
    }
}
