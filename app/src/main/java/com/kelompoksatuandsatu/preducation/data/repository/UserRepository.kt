package com.kelompoksatuandsatu.preducation.data.repository

import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.UserDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login.LoginRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.register.RegisterRequest
import com.kelompoksatuandsatu.preducation.model.auth.UserAuth
import com.kelompoksatuandsatu.preducation.model.auth.UserLogin
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import com.kelompoksatuandsatu.preducation.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun userRegister(request: UserAuth): Flow<ResultWrapper<String>>

    suspend fun userLogin(request: UserLogin): Flow<ResultWrapper<Boolean>>

    suspend fun createResetPassword(resetPasswordRequest: ResetPasswordRequest): Flow<ResultWrapper<ResetPasswordResponse>>

}

class UserRepositoryImpl(
    private val dataSource: UserDataSource,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : UserRepository {
    override suspend fun userRegister(request: UserAuth): Flow<ResultWrapper<String>> {
        return proceedFlow {
            val dataRequest =
                RegisterRequest(request.email, request.name, request.phone, request.password)
            dataSource.userRegister(dataRequest).message.toString()
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


    override suspend fun createResetPassword(resetPasswordRequest: ResetPasswordRequest): Flow<ResultWrapper<ResetPasswordResponse>> {
        return proceedFlow { dataSource.createResetPassword(resetPasswordRequest) }
    }
}
