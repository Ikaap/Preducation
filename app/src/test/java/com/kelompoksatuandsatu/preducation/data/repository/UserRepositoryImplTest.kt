package com.kelompoksatuandsatu.preducation.data.repository

import app.cash.turbine.test
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.UserDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.forgotpassword.ForgotPasswordResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login.LoginResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.logout.LogoutResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.postemail.EmailOtpResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.verifyotp.OtpResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.register.Data
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.register.RegisterResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.User
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.UserResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.changepassword.ChangePasswordItem
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.changepassword.ChangePasswordRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.changepassword.ChangePasswordResponse
import com.kelompoksatuandsatu.preducation.model.auth.UserAuth
import com.kelompoksatuandsatu.preducation.model.auth.UserLogin
import com.kelompoksatuandsatu.preducation.model.auth.forgotpassword.UserForgotPassword
import com.kelompoksatuandsatu.preducation.model.auth.otp.postemailotp.EmailOtp
import com.kelompoksatuandsatu.preducation.model.auth.otp.verifyotp.OtpData
import com.kelompoksatuandsatu.preducation.utils.AssetWrapper
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class UserRepositoryImplTest {

    @MockK
    lateinit var userDataSource: UserDataSource

    @MockK
    lateinit var userPreferenceDataSource: UserPreferenceDataSource

    @MockK
    lateinit var assetWrapper: AssetWrapper

    private lateinit var userRepo: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userRepo = UserRepositoryImpl(userDataSource, assetWrapper, userPreferenceDataSource)
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

    @Test
    fun `get user by id, result error`() {
        runTest {
            coEvery { userDataSource.getUserById(any()) } throws Exception("error")
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
        val mockRequest = mockk<RequestBody>(relaxed = true)
        val mockRequestMultiPart = mockk<MultipartBody.Part>(relaxed = true)
        runTest {
            coEvery {
                userDataSource.updateUserById(
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns mockUserResponse
            userRepo.updateUserById(
                "u1",
                mockRequest,
                mockRequest,
                mockRequest,
                mockRequest,
                mockRequest,
                mockRequestMultiPart
            ).map {
                delay(100)
                it
            }.test {
                delay(2130)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Loading)
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
        val mockRequestMultiPart = mockk<MultipartBody.Part>(relaxed = true)
        runTest {
            coEvery {
                userDataSource.updateUserById(
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns fakeUserResponse
            userRepo.updateUserById(
                "u1",
                "name".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                "email".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                "phone".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                "country".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                "city".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                mockRequestMultiPart
            ).map {
                delay(100)
                it
            }.test {
                delay(2330)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Success)
                assertEquals(result.payload?.id, "u1")
                coVerify {
                    userDataSource.updateUserById(
                        any(),
                        any(),
                        any(),
                        any(),
                        any(),
                        any(),
                        any()
                    )
                }
            }
        }
    }

    @Test
    fun `update user by id, result error`() {
        val mockRequestMultiPart = mockk<MultipartBody.Part>(relaxed = true)
        runTest {
            coEvery {
                userDataSource.updateUserById(
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } throws IllegalStateException("Error")
            userRepo.updateUserById(
                "u1",
                "name".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                "email".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                "phone".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                "country".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                "city".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                mockRequestMultiPart
            ).map {
                delay(100)
                it
            }.test {
                delay(2330)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Error)
                coVerify {
                    userDataSource.updateUserById(
                        any(),
                        any(),
                        any(),
                        any(),
                        any(),
                        any(),
                        any()
                    )
                }
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
            coEvery {
                userDataSource.updateUserPassword(
                    any(),
                    any()
                )
            } returns mockChangePasswordResponse
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
            coEvery {
                userDataSource.updateUserPassword(
                    any(),
                    any()
                )
            } returns fakeChangePasswordResponse
            userRepo.updateUserPassword("u1", fakeChangePasswordRequest).map {
                delay(100)
                it
            }.test {
                delay(230)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Success)
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
            coEvery {
                userDataSource.updateUserPassword(
                    any(),
                    any()
                )
            } throws IllegalStateException("error")
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
    @Test
    fun `user register , result loading`() {
        val mockRegisterResponse = mockk<RegisterResponse>()
        val fakeRegisterRequest = UserAuth(
            email = "email",
            name = "name",
            phone = "0899",
            password = "password"
        )
        runTest {
            coEvery { userDataSource.userRegister(any()) } returns mockRegisterResponse
            userRepo.userRegister(fakeRegisterRequest).map {
                delay(100)
                it
            }.test {
                delay(130)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Loading)
                coVerify { userDataSource.userRegister(any()) }
//                coVerify(exactly = 0) { mockRegisterResponse.toRegisterResponse() }
            }
        }
    }

    @Test
    fun `user register  , result success`() {
        val fakeRegisterData = Data(
            id = "id",
            phone = "089",
            role = "user",
            name = "name",
            accessToken = "token"
        )
        val fakeRegisterResponse = RegisterResponse(
            message = "message",
            success = true,
            data = fakeRegisterData
        )
        val fakeRegisterRequest = UserAuth(
            email = "email",
            name = "name",
            phone = "0899",
            password = "password"
        )
        runTest {
            coEvery { userDataSource.userRegister(any()) } returns fakeRegisterResponse
            userRepo.userRegister(fakeRegisterRequest).map {
                delay(100)
                it
            }.test {
                delay(230)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Success)
                coVerify { userDataSource.userRegister(any()) }
//                coVerify { fakeRegisterResponse.toRegisterResponse() }
            }
        }
    }

    @Test
    fun `user register  , result error`() {
        val mockRegisterResponse = mockk<RegisterResponse>()
        val fakeRegisterRequest = UserAuth(
            email = "email",
            name = "name",
            phone = "0899",
            password = "password"
        )
        runTest {
            coEvery { userDataSource.userRegister(any()) } throws IllegalStateException("error")
            userRepo.userRegister(fakeRegisterRequest).map {
                delay(100)
                it
            }.test {
                delay(230)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Error)
                coVerify { userDataSource.userRegister(any()) }
//                coVerify { userDataSource.userRegister(any()).toRegisterResponse() }
            }
        }
    }

    // user login
    @Test
    fun `user login , result loading`() {
        val mockLoginResponse = mockk<LoginResponse>()
        val fakeLoginRequest = UserLogin(
            identifier = "email",
            password = "password"
        )
        runTest {
            coEvery { userDataSource.userLogin(any()) } returns mockLoginResponse
            userRepo.userLogin(fakeLoginRequest).map {
                delay(100)
                it
            }.test {
                delay(130)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Loading)
                coVerify { userDataSource.userLogin(any()) }
            }
        }
    }

    @Test
    fun `user login, result success`() {
        runTest {
            val fakeLoginData =
                com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login.Data(
                    id = "id",
                    accessToken = "token"
                )
            val fakeLoginResponse = LoginResponse(
                message = "message",
                success = true,
                data = fakeLoginData
            )
            val fakeLoginRequest = UserLogin(
                identifier = "email",
                password = "password"
            )
            coEvery { userDataSource.userLogin(any()) } returns fakeLoginResponse
            userRepo.userLogin(fakeLoginRequest).map {
                delay(100)
                it
            }.test {
                delay(230)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Success)
                coVerify { userDataSource.userLogin(any()) }
//                coVerify { userDataSource.userLogin(any()).toLoginResponse() }
            }
        }
    }

    @Test
    fun `user login, result error`() {
        val fakeLoginRequest = UserLogin(
            identifier = "email",
            password = "password"
        )
        runTest {
            coEvery { userDataSource.userLogin(any()) } throws IllegalStateException("error")
            userRepo.userLogin(fakeLoginRequest).map {
                delay(100)
                it
            }.test {
                delay(230)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Error)
                coVerify { userDataSource.userLogin(any()) }
            }
        }
    }

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

    @Test
    fun `user logout, result loading`() {
        val mockLogoutResponse = mockk<LogoutResponse>()
        runTest {
            coEvery { userDataSource.userLogout() } returns Response.success(mockLogoutResponse)
            userRepo.userLogout().map {
                delay(100)
                it
            }.test {
                delay(130)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Loading)
                coVerify { userDataSource.userLogout() }
            }
        }
    }

    @Test
    fun `user logout, result success`() {
        val fakeLogoutResponse = LogoutResponse(
            message = "message",
            status = "status",
            success = true
        )
        runTest {
            coEvery { userDataSource.userLogout() } returns Response.success(fakeLogoutResponse)
            userRepo.userLogout().map {
                delay(100)
                it
            }.test {
                delay(230)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Success)
                coVerify { userDataSource.userLogout() }
            }
        }
    }

    // verify otp empty

    @Test
    fun `user logout, result error`() {
        runTest {
            coEvery { userDataSource.userLogout() } throws IllegalStateException("error")
            userRepo.userLogout().map {
                delay(100)
                it
            }.test {
                delay(230)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Error)
                coVerify { userDataSource.userLogout() }
            }
        }
    }
}
