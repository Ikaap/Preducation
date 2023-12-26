package com.kelompoksatuandsatu.preducation.data.network.api.datasource

import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.forgotpassword.ForgotPasswordRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.forgotpassword.ForgotPasswordResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login.LoginRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login.LoginResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.postemail.EmailOtpRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.postemail.EmailOtpResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.verifyotp.OtpRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.verifyotp.OtpResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.register.RegisterRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.register.RegisterResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.changepassword.ChangePasswordRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.changepassword.ChangePasswordResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.UserRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.UserResponse
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

class UserDataSourceImplTest {

    @MockK
    lateinit var service: PreducationService

    private lateinit var userDataSource: UserDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userDataSource = UserDataSourceImpl(service)
    }

    @Test
    fun userRegister() {
        runTest {
            val mockResponse = mockk<RegisterResponse>(relaxed = true)
            val mockRequest = mockk<RegisterRequest>(relaxed = true)
            coEvery { service.userRegister(any()) } returns mockResponse
            val response = userDataSource.userRegister(mockRequest)
            coVerify { service.userRegister(any()) }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun userLogin() {
        runTest {
            val mockResponse = mockk<LoginResponse>(relaxed = true)
            val mockRequest = mockk<LoginRequest>(relaxed = true)
            coEvery { service.userLogin(any()) } returns mockResponse
            val response = userDataSource.userLogin(mockRequest)
            coVerify { service.userLogin(any()) }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun postEmailOtp() {
        runTest {
            val mockResponse = mockk<EmailOtpResponse>(relaxed = true)
            val mockRequest = mockk<EmailOtpRequest>(relaxed = true)
            coEvery { service.postEmailOtp(any()) } returns mockResponse
            val response = userDataSource.postEmailOtp(mockRequest)
            coVerify { service.postEmailOtp(any()) }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun verifyOtp() {
        runTest {
            val mockResponse = mockk<OtpResponse>(relaxed = true)
            val mockRequest = mockk<OtpRequest>(relaxed = true)
            coEvery { service.verifyOtp(any()) } returns mockResponse
            val response = userDataSource.verifyOtp(mockRequest)
            coVerify { service.verifyOtp(any()) }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun getUserById() {
        runTest {
            val mockResponse = mockk<UserResponse>(relaxed = true)
            coEvery { service.getUserById(any()) } returns mockResponse
            val response = userDataSource.getUserById("1")
            coVerify { service.getUserById(any()) }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun updateUserById() {
        runTest {
            val mockResponse = mockk<UserResponse>(relaxed = true)
            val mockRequest = mockk<UserRequest>(relaxed = true)
            coEvery { service.updateUserById(any(), any()) } returns mockResponse
            val response = userDataSource.updateUserById("1", mockRequest)
            coVerify { service.updateUserById(any(), any()) }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun updateUserPassword() {
        runTest {
            val mockResponse = mockk<ChangePasswordResponse>(relaxed = true)
            val mockRequest = mockk<ChangePasswordRequest>(relaxed = true)
            coEvery { service.updateUserPassword(any(), any()) } returns mockResponse
            val response = userDataSource.updateUserPassword("1", mockRequest)
            coVerify { service.updateUserPassword(any(), any()) }
            assertEquals(response, mockResponse)
        }
    }

//    @Test
//    fun userLogout() {
//        runTest {
//            val mockResponse = mockk<LogoutResponse>()
//            coEvery { service.userLogout() } returns mockResponse
//            val response = userDataSource.userLogout()
//            coVerify { service.userLogout() }
//            assertEquals(response, mockResponse)
//        }
//    }

    @Test
    fun userForgotPassword() {
        runTest {
            val mockResponse = mockk<ForgotPasswordResponse>(relaxed = true)
            val mockRequest = mockk<ForgotPasswordRequest>(relaxed = true)
            coEvery { service.userForgotPassword(any()) } returns mockResponse
            val response = userDataSource.userForgotPassword(mockRequest)
            coVerify { service.userForgotPassword(any()) }
            assertEquals(response, mockResponse)
        }
    }
}
