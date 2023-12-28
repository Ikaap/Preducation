package com.kelompoksatuandsatu.preducation.data.repository

import app.cash.turbine.test
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.UserDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.forgotpassword.ForgotPasswordResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.postemail.EmailOtpResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.verifyotp.OtpResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.changepassword.ChangePasswordItem
import com.kelompoksatuandsatu.preducation.data.network.api.model.changepassword.ChangePasswordRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.changepassword.ChangePasswordResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.User
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.UserRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.UserResponse
import com.kelompoksatuandsatu.preducation.model.auth.forgotpassword.UserForgotPassword
import com.kelompoksatuandsatu.preducation.model.auth.otp.postemailotp.EmailOtp
import com.kelompoksatuandsatu.preducation.model.auth.otp.verifyotp.OtpData
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

class UserRepositoryImplTest {

    @MockK
    lateinit var userDataSource: UserDataSource

    @MockK
    lateinit var userPreferenceDataSource: UserPreferenceDataSource

    private lateinit var userRepo: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userRepo = UserRepositoryImpl(userDataSource, userPreferenceDataSource)
    }

    @Test
    fun `get user by id, result loading`() {
        val mockUserResponse = mockk<UserResponse>()
        runTest {
            coEvery { userDataSource.getUserById(any()) } returns mockUserResponse
            userRepo.getUserById("u1").map {
                delay(100)
                it
            }.test {
                delay(130)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Loading)
                coVerify { userDataSource.getUserById(any()) }
            }
        }
    }

    @Test
    fun `get user by id, result success`() {
        val fakeUserData = User(
            id = "u1",
            email = "email",
            phone = "085277327",
            name = "nama",
            username = "username",
            imageProfile = "url",
            country = "country",
            city = "city"
        )
        val fakeUserResponse = UserResponse(
            data = fakeUserData,
            message = "success",
            success = true
        )
        runTest {
            coEvery { userDataSource.getUserById(any()) } returns fakeUserResponse
            userRepo.getUserById("u1").map {
                delay(100)
                it
            }.test {
                delay(230)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Success)
                assertEquals(result.payload?.id, "u1")
                coVerify { userDataSource.getUserById(any()) }
            }
        }
    }

    /*
    @Test
    fun `get user by id, result empty`() {
//        val fakeUserData = User(
//            id = "",
//            email = "",
//            phone = "",
//            name = "",
//            username = "",
//            imageProfile = "",
//            country = "",
//            city = ""
//        )
        val mockUser = mockk<User>()
        val fakeUserResponse = UserResponse(
            data = mockUser,
            message = "success",
            success = true
        )
        runTest {
            coEvery { userDataSource.getUserById(any()) } returns fakeUserResponse
            userRepo.getUserById("u1").map {
                delay(100)
                it
            }.test {
                delay(230)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Empty)
                coVerify { userDataSource.getUserById(any()) }
            }
        }
    }

     */

    @Test
    fun `get user by id, result error`() {
        runTest {
            coEvery { userDataSource.getUserById(any()) } throws IllegalStateException("error")
            userRepo.getUserById("u1").map {
                delay(100)
                it
            }.test {
                delay(230)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Error)
                coVerify { userDataSource.getUserById(any()) }
            }
        }
    }

    @Test
    fun `update user by id, result loading`() {
        val mockUserResponse = mockk<UserResponse>()
        val fakeUserRequest = UserRequest(
            email = "email",
            phone = "08999090",
            name = "name",
            imageProfile = "url",
            city = "city",
            country = "country"
        )
        runTest {
            coEvery { userDataSource.updateUserById(any(), any()) } returns mockUserResponse
            userRepo.updateUserById("u1", fakeUserRequest).map {
                delay(100)
                it
            }.test {
                delay(130)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Loading)
                coVerify { userDataSource.updateUserById(any(), any()) }
            }
        }
    }

    @Test
    fun `update user by id, result success`() {
        val fakeUserData = User(
            id = "u1",
            email = "email",
            phone = "085277327",
            name = "nama",
            username = "username",
            imageProfile = "url",
            country = "country",
            city = "city"
        )
        val fakeUserResponse = UserResponse(
            data = fakeUserData,
            message = "success",
            success = true
        )
        val fakeUserRequest = UserRequest(
            email = "email",
            phone = "08999090",
            name = "name",
            imageProfile = "url",
            city = "city",
            country = "country"
        )
        runTest {
            coEvery { userDataSource.updateUserById(any(), any()) } returns fakeUserResponse
            userRepo.updateUserById("u1", fakeUserRequest).map {
                delay(100)
                it
            }.test {
                delay(230)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Success)
                assertEquals(result.payload?.id, "u1")
                coVerify { userDataSource.updateUserById(any(), any()) }
            }
        }
    }

    // update user empty

    @Test
    fun `update user by id, result error`() {
        val fakeUserRequest = UserRequest(
            email = "email",
            phone = "08999090",
            name = "name",
            imageProfile = "url",
            city = "city",
            country = "country"
        )
        runTest {
            coEvery { userDataSource.updateUserById(any(), any()) } throws IllegalStateException("Error")
            userRepo.updateUserById("u1", fakeUserRequest).map {
                delay(100)
                it
            }.test {
                delay(230)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Error)
                coVerify { userDataSource.updateUserById(any(), any()) }
            }
        }
    }

    @Test
    fun `update user password , result loading`() {
        val mockChangePasswordResponse = mockk<ChangePasswordResponse>()
        val fakeChangePasswordRequest = ChangePasswordRequest(
            oldPassword = "old pass",
            newPassword = "new pass",
            confirmPassword = "new pass"
        )
        runTest {
            coEvery { userDataSource.updateUserPassword(any(), any()) } returns mockChangePasswordResponse
            userRepo.updateUserPassword("u1", fakeChangePasswordRequest).map {
                delay(100)
                it
            }.test {
                delay(130)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Loading)
                coVerify { userDataSource.updateUserPassword(any(), any()) }
            }
        }
    }

    @Test
    fun `update user password , result success`() {
        val fakeChangePasswordItem = ChangePasswordItem(
            id = "cp1",
            oldPassword = "old pass",
            newPassword = "new pass",
            confirmPassword = "new pass"
        )
        val fakeChangePasswordResponse = ChangePasswordResponse(
            data = listOf(fakeChangePasswordItem),
            message = "message",
            status = "status",
            success = true
        )
        val fakeChangePasswordRequest = ChangePasswordRequest(
            oldPassword = "old pass",
            newPassword = "new pass",
            confirmPassword = "new pass"
        )
        runTest {
            coEvery { userDataSource.updateUserPassword(any(), any()) } returns fakeChangePasswordResponse
            userRepo.updateUserPassword("u1", fakeChangePasswordRequest).map {
                delay(100)
                it
            }.test {
                delay(230)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Success)
                assertTrue(result.payload?.get(0)?.newPassword == result.payload?.get(0)?.confirmPassword)
                coVerify { userDataSource.updateUserPassword(any(), any()) }
            }
        }
    }

    @Test
    fun `update user password , result empty`() {
        val fakeChangePasswordResponse = ChangePasswordResponse(
            data = emptyList(),
            message = "message",
            status = "status",
            success = true
        )
        val fakeChangePasswordRequest = ChangePasswordRequest(
            oldPassword = "old pass",
            newPassword = "new pass",
            confirmPassword = "new pass"
        )
        runTest {
            coEvery { userDataSource.updateUserPassword(any(), any()) } returns fakeChangePasswordResponse
            userRepo.updateUserPassword("u1", fakeChangePasswordRequest).map {
                delay(100)
                it
            }.test {
                delay(230)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Empty)
                coVerify { userDataSource.updateUserPassword(any(), any()) }
            }
        }
    }

    @Test
    fun `update user password , result error`() {
        val fakeChangePasswordRequest = ChangePasswordRequest(
            oldPassword = "old pass",
            newPassword = "new pass",
            confirmPassword = "new pass"
        )
        runTest {
            coEvery { userDataSource.updateUserPassword(any(), any()) } throws IllegalStateException("error")
            userRepo.updateUserPassword("u1", fakeChangePasswordRequest).map {
                delay(100)
                it
            }.test {
                delay(230)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Error)
                coVerify { userDataSource.updateUserPassword(any(), any()) }
            }
        }
    }

    // user register

    // user login

    @Test
    fun `post email otp , result loading`() {
        val mockEmailOtpResponse = mockk<EmailOtpResponse>()
        val fakePostEmailOtpRequest = EmailOtp(
            email = "email"
        )
        runTest {
            coEvery { userDataSource.postEmailOtp(any()) } returns mockEmailOtpResponse
            userRepo.postEmailOtp(fakePostEmailOtpRequest).map {
                delay(100)
                it
            }.test {
                delay(130)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Loading)
                coVerify { userDataSource.postEmailOtp(any()) }
            }
        }
    }

    @Test
    fun `post email otp , result success`() {
        val fakePostEmailOtpResponse = EmailOtpResponse(
            message = "message",
            status = "status",
            success = true
        )
        val fakePostEmailOtpRequest = EmailOtp(
            email = "email"
        )
        runTest {
            coEvery { userDataSource.postEmailOtp(any()) } returns fakePostEmailOtpResponse
            userRepo.postEmailOtp(fakePostEmailOtpRequest).map {
                delay(100)
                it
            }.test {
                delay(230)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Success)
                assertTrue(result.payload != null)
                coVerify { userDataSource.postEmailOtp(any()) }
            }
        }
    }

    /*
    @Test
    fun `post email otp , result empty`() {
        val fakePostEmailOtpResponse = EmailOtpResponse(
            message = "",
            status = "",
            success = null
        )
        val fakePostEmailOtpRequest = EmailOtp(
            email = "email"
        )
        runTest {
            coEvery { userDataSource.postEmailOtp(any()) } returns fakePostEmailOtpResponse
            userRepo.postEmailOtp(fakePostEmailOtpRequest).map {
                delay(100)
                it
            }.test {
                delay(230)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Empty)
                coVerify { userDataSource.postEmailOtp(any()) }
            }
        }
    }
    */

    @Test
    fun `post email otp , result error`() {
        val fakePostEmailOtpRequest = EmailOtp(
            email = "email"
        )
        runTest {
            coEvery { userDataSource.postEmailOtp(any()) } throws IllegalStateException("error")
            userRepo.postEmailOtp(fakePostEmailOtpRequest).map {
                delay(100)
                it
            }.test {
                delay(230)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Error)
                coVerify { userDataSource.postEmailOtp(any()) }
            }
        }
    }

    @Test
    fun `verify otp , result loading`() {
        val mockVerifyOtpResponse = mockk<OtpResponse>()
        val fakeVerifyOtpRequest = OtpData(
            otp = "123456"
        )
        runTest {
            coEvery { userDataSource.verifyOtp(any()) } returns mockVerifyOtpResponse
            userRepo.verifyOtp(fakeVerifyOtpRequest).map {
                delay(100)
                it
            }.test {
                delay(130)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Loading)
                coVerify { userDataSource.verifyOtp(any()) }
            }
        }
    }

    @Test
    fun `verify otp , result success`() {
        val fakeVerifyOtpResponse = OtpResponse(
            message = "message",
            status = "status",
            success = true
        )
        val fakeVerifyOtpRequest = OtpData(
            otp = "123456"
        )
        runTest {
            coEvery { userDataSource.verifyOtp(any()) } returns fakeVerifyOtpResponse
            userRepo.verifyOtp(fakeVerifyOtpRequest).map {
                delay(100)
                it
            }.test {
                delay(230)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Success)
                assertTrue(result.payload != null)
                coVerify { userDataSource.verifyOtp(any()) }
            }
        }
    }

    // verify otp empty

    @Test
    fun `verify otp , result error`() {
        val fakeVerifyOtpRequest = OtpData(
            otp = "123456"
        )
        runTest {
            coEvery { userDataSource.verifyOtp(any()) } throws IllegalStateException("error")
            userRepo.verifyOtp(fakeVerifyOtpRequest).map {
                delay(100)
                it
            }.test {
                delay(230)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Error)
                coVerify { userDataSource.verifyOtp(any()) }
            }
        }
    }

    @Test
    fun `user forgot password, result loading`() {
        val mockForgotPasswordResponse = mockk<ForgotPasswordResponse>()
        val fakeForgotPasswordRequest = UserForgotPassword(
            email = "email"
        )
        runTest {
            coEvery { userDataSource.userForgotPassword(any()) } returns mockForgotPasswordResponse
            userRepo.userForgotPassword(fakeForgotPasswordRequest).map {
                delay(100)
                it
            }.test {
                delay(130)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Loading)
                coVerify { userDataSource.userForgotPassword(any()) }
            }
        }
    }

    @Test
    fun `user forgot password, result success`() {
        val fakeForgotPasswordResponse = ForgotPasswordResponse(
            message = "message",
            status = "status",
            success = true
        )
        val fakeForgotPasswordRequest = UserForgotPassword(
            email = "email"
        )
        runTest {
            coEvery { userDataSource.userForgotPassword(any()) } returns fakeForgotPasswordResponse
            userRepo.userForgotPassword(fakeForgotPasswordRequest).map {
                delay(100)
                it
            }.test {
                delay(230)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Success)
                coVerify { userDataSource.userForgotPassword(any()) }
            }
        }
    }

    // verify otp empty

    @Test
    fun `user forgot password, result error`() {
        val fakeForgotPasswordRequest = UserForgotPassword(
            email = "email"
        )
        runTest {
            coEvery { userDataSource.userForgotPassword(any()) } throws IllegalStateException("error")
            userRepo.userForgotPassword(fakeForgotPasswordRequest).map {
                delay(100)
                it
            }.test {
                delay(230)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Error)
                coVerify { userDataSource.userForgotPassword(any()) }
            }
        }
    }
}
