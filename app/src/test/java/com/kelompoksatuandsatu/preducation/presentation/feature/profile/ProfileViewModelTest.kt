package com.kelompoksatuandsatu.preducation.presentation.feature.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.model.user.UserViewParam
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

class ProfileViewModelTest {

    @MockK
    private lateinit var userRepo: UserRepository

    @MockK
    private lateinit var userPreferenceDataSource: UserPreferenceDataSource

    private lateinit var viewModel: ProfileViewModel

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = spyk(ProfileViewModel(userRepo, userPreferenceDataSource))

        val resultUser = flow {
            emit(ResultWrapper.Success(UserViewParam("id", "email", "089", "name", "image", "country", "city")))
        }

        val resultMock = flow {
            emit(ResultWrapper.Success(true))
        }
        coEvery { userPreferenceDataSource.getUserId() } returns ""
        coEvery { userRepo.getUserById(any()) } returns resultUser
        coEvery { userRepo.userLogout() } returns resultMock
        coEvery { userPreferenceDataSource.getUserToken() } returns ""
    }

    @Test
    fun `get user by id`() {
        viewModel.getUserById()
        coVerify { userRepo.getUserById(any()) }
    }

    @Test
    fun `get user logout`() {
        viewModel.userLogout()
        coVerify { userRepo.userLogout() }
    }

    @Test
    fun `check login user`() {
        viewModel.checkLogin()
        coVerify { userPreferenceDataSource.getUserToken() }
    }
}
