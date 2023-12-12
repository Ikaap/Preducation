package com.kelompoksatuandsatu.preducation.data.repository

import com.kelompoksatuandsatu.preducation.data.network.api.datasource.UserDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.changepassword.ChangePasswordRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.changepassword.ChangePasswordResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.logout.UserLogoutResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.UserRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.UserResponse
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.UserDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login.LoginRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.register.RegisterRequest
import com.kelompoksatuandsatu.preducation.model.auth.UserAuth
import com.kelompoksatuandsatu.preducation.model.auth.UserLogin
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import com.kelompoksatuandsatu.preducation.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

import com.kelompoksatuandsatu.preducation.utils.proceedFlow

interface UserRepository {
    suspend fun getUserById(id: String? = null): UserResponse

    suspend fun updateUserById(id: String, userRequest: UserRequest): UserResponse
    suspend fun updateUserPassword(
        id: String,
        passwordRequest: ChangePasswordRequest
    ): ChangePasswordResponse

    suspend fun performLogout(): UserLogoutResponse

    suspend fun userRegister(request: UserAuth): Flow<ResultWrapper<String>>

    suspend fun userLogin(request: UserLogin): Flow<ResultWrapper<Boolean>>

}

class UserRepositoryImpl(private val userDataSource: UserDataSource) : UserRepository {

    override suspend fun getUserById(id: String?): UserResponse {
        return proceedFlow {
            userDataSource.getUserById()
        }
    }

    override suspend fun updateUserById(id: String, userRequest: UserRequest): UserResponse {
        return userDataSource.updateUserById(id, userRequest)
    }

    override suspend fun updateUserPassword(
        id: String,
        passwordRequest: ChangePasswordRequest
    ): ChangePasswordResponse {
        return userDataSource.updateUserPassword(id, passwordRequest)
    }

    override suspend fun performLogout(): UserLogoutResponse {
        return userDataSource.performLogout()
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
            val loginResult = dataSource.userLogin(dataReq)
            if (loginResult.success) {
                userPreferenceDataSource.saveUserToken(loginResult.data.accessToken)
            }
            loginResult.success
        }
    }
}
