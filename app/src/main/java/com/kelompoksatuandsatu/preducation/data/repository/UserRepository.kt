package com.kelompoksatuandsatu.preducation.data.repository

import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.UserDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.forgotpassword.ForgotPasswordRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login.LoginRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login.toLoginResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.postemail.EmailOtpRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.verifyotp.OtpRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.verifyotp.toOtpResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.register.RegisterRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.register.toRegisterResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.changepassword.ChangePasswordRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.toUserViewParam
import com.kelompoksatuandsatu.preducation.model.auth.UserAuth
import com.kelompoksatuandsatu.preducation.model.auth.UserLogin
import com.kelompoksatuandsatu.preducation.model.auth.UserLoginResponse
import com.kelompoksatuandsatu.preducation.model.auth.UserRegisterResponse
import com.kelompoksatuandsatu.preducation.model.auth.forgotpassword.UserForgotPassword
import com.kelompoksatuandsatu.preducation.model.auth.otp.postemailotp.EmailOtp
import com.kelompoksatuandsatu.preducation.model.auth.otp.verifyotp.OtpData
import com.kelompoksatuandsatu.preducation.model.common.BaseResponse
import com.kelompoksatuandsatu.preducation.model.user.UserViewParam
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import com.kelompoksatuandsatu.preducation.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface UserRepository {
    suspend fun getUserById(id: String): Flow<ResultWrapper<UserViewParam>>

    suspend fun updateUserById(
        id: String,
        name: RequestBody?,
        email: RequestBody?,
        phone: RequestBody?,
        country: RequestBody?,
        city: RequestBody?,
        imageProfile: MultipartBody.Part?
    ): Flow<ResultWrapper<UserViewParam>>

    suspend fun updateUserPassword(
        id: String,
        passwordRequest: ChangePasswordRequest
    ): Flow<ResultWrapper<Boolean>>

    suspend fun userRegister(request: UserAuth): Flow<ResultWrapper<UserRegisterResponse>>

    suspend fun userLogin(request: UserLogin): Flow<ResultWrapper<UserLoginResponse>>

    suspend fun postEmailOtp(request: EmailOtp): Flow<ResultWrapper<Boolean>>

    suspend fun verifyOtp(request: OtpData): Flow<ResultWrapper<BaseResponse>>
    suspend fun userLogout(): Flow<ResultWrapper<Boolean>>

    suspend fun userForgotPassword(request: UserForgotPassword): Flow<ResultWrapper<Boolean>>
}

class UserRepositoryImpl(
    private val userDataSource: UserDataSource,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : UserRepository {

    override suspend fun getUserById(id: String): Flow<ResultWrapper<UserViewParam>> {
        return proceedFlow {
            userDataSource.getUserById(id).data?.toUserViewParam()!!
        }
    }

    override suspend fun updateUserById(
        id: String,
        name: RequestBody?,
        email: RequestBody?,
        phone: RequestBody?,
        country: RequestBody?,
        city: RequestBody?,
        imageProfile: MultipartBody.Part?
    ): Flow<ResultWrapper<UserViewParam>> {
        return proceedFlow {
            userDataSource.updateUserById(
                id,
                name,
                email,
                phone,
                country,
                city,
                imageProfile
            ).data?.toUserViewParam()!!
        }.catch {
            emit(ResultWrapper.Error(Exception(it)))
        }.onStart {
            emit(ResultWrapper.Loading())
            delay(2000)
        }
    }

    override suspend fun updateUserPassword(
        id: String,
        passwordRequest: ChangePasswordRequest
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            userDataSource.updateUserPassword(id, passwordRequest).success
        }
    }

    override suspend fun userRegister(request: UserAuth): Flow<ResultWrapper<UserRegisterResponse>> {
        return proceedFlow {
            val dataRequest =
                RegisterRequest(request.email, request.name, request.phone, request.password)
            val regisResult = userDataSource.userRegister(dataRequest)
            if (regisResult.success) {
                userPreferenceDataSource.saveUserToken(regisResult.data.accessToken)
            }
            regisResult.toRegisterResponse()
        }
    }

    override suspend fun userLogin(request: UserLogin): Flow<ResultWrapper<UserLoginResponse>> {
        return proceedFlow {
            val dataReq = LoginRequest(request.identifier, request.password)
            val loginResult = userDataSource.userLogin(dataReq)
            if (loginResult.success) {
                userPreferenceDataSource.saveUserToken(loginResult.data.accessToken)
            }
            loginResult.toLoginResponse()
        }
    }

    override suspend fun postEmailOtp(request: EmailOtp): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            val emailRequest = EmailOtpRequest(request.email)
            val emailResult = userDataSource.postEmailOtp(emailRequest)
            emailResult.success == true
        }
    }

    override suspend fun verifyOtp(request: OtpData): Flow<ResultWrapper<BaseResponse>> {
        return proceedFlow {
            val otpRequest = OtpRequest(request.otp)
            val otpResult = userDataSource.verifyOtp(otpRequest)
            otpResult.toOtpResponse()
        }
    }

    override suspend fun userForgotPassword(request: UserForgotPassword): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            val dataRequest = ForgotPasswordRequest(request.email)
            val forgotPasswordResult = userDataSource.userForgotPassword(dataRequest)
            forgotPasswordResult.success
        }
    }

    override suspend fun userLogout(): Flow<ResultWrapper<Boolean>> = flow {
        val response = userDataSource.userLogout()
        if (response.isSuccessful) {
            userPreferenceDataSource.deleteAllData()
            emit(ResultWrapper.Success(true))
        } else {
            emit(ResultWrapper.Error(Exception("Logout failed with response code ${response.code()}")))
        }
    }.catch { e ->
        emit(ResultWrapper.Error(e as? Exception ?: Exception("Unknown error")))
    }
}
