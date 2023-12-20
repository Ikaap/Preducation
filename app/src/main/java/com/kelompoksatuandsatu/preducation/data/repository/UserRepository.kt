package com.kelompoksatuandsatu.preducation.data.repository

import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.UserDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login.LoginRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.OtpRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.register.RegisterRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.changepassword.ChangePasswordRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.changepassword.toPasswordList
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.UserRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.toUserViewParam
import com.kelompoksatuandsatu.preducation.model.auth.OtpData
import com.kelompoksatuandsatu.preducation.model.auth.UserAuth
import com.kelompoksatuandsatu.preducation.model.auth.UserLogin
import com.kelompoksatuandsatu.preducation.model.user.Password
import com.kelompoksatuandsatu.preducation.model.user.UserViewParam
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import com.kelompoksatuandsatu.preducation.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserById(id: String? = null): Flow<ResultWrapper<UserViewParam>>

    suspend fun updateUserById(id: String, userRequest: UserRequest): Flow<ResultWrapper<UserViewParam>>
    suspend fun updateUserPassword(
        id: String,
        passwordRequest: ChangePasswordRequest
    ): Flow<ResultWrapper<List<Password>>>

    suspend fun performLogout(): Flow<ResultWrapper<Boolean>>

    suspend fun userRegister(request: UserAuth): Flow<ResultWrapper<String>>

    suspend fun userLogin(request: UserLogin): Flow<ResultWrapper<Boolean>>

    suspend fun userOtp(request: OtpData): Flow<ResultWrapper<Boolean>>
}

class UserRepositoryImpl(private val userDataSource: UserDataSource, private val userPreferenceDataSource: UserPreferenceDataSource) : UserRepository {

    override suspend fun getUserById(id: String?): Flow<ResultWrapper<UserViewParam>> {
        return proceedFlow {
            userDataSource.getUserById(id).data?.toUserViewParam()!!
        }
    }

    override suspend fun updateUserById(
        id: String,
        userRequest: UserRequest
    ): Flow<ResultWrapper<UserViewParam>> {
        return proceedFlow {
            userDataSource.updateUserById(id, userRequest).data?.toUserViewParam()!!
        }
    }

    override suspend fun updateUserPassword(
        id: String,
        passwordRequest: ChangePasswordRequest
    ): Flow<ResultWrapper<List<Password>>> {
        return proceedFlow {
            (userDataSource.updateUserPassword(id, passwordRequest).data?.toPasswordList() ?: emptyList())
        }
    }

    override suspend fun performLogout(): Flow<ResultWrapper<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun userRegister(request: UserAuth): Flow<ResultWrapper<String>> {
        return proceedFlow {
            val dataRequest =
                RegisterRequest(request.email, request.name, request.phone, request.password)
            userDataSource.userRegister(dataRequest).message.toString()
        }
    }

    override suspend fun userLogin(request: UserLogin): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            val dataReq = LoginRequest(request.identifier, request.password)
            val loginResult = userDataSource.userLogin(dataReq)
            if (loginResult.success) {
                userPreferenceDataSource.saveUserToken(loginResult.data.accessToken)
            }
            loginResult.success
        }
    }

    override suspend fun userOtp(request: OtpData): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            val otpRequest = OtpRequest(request.email)
            val otpResult = userDataSource.userOtp(otpRequest)
            otpResult.success == true
        }
    }
}
