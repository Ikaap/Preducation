package com.kelompoksatuandsatu.preducation.data.repository

import com.kelompoksatuandsatu.preducation.data.network.api.datasource.UserDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.ResetPasswordRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.ResetPasswordResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login.LoginRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login.toToken
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.register.RegisterRequest
import com.kelompoksatuandsatu.preducation.model.auth.LoginToken
import com.kelompoksatuandsatu.preducation.model.auth.UserAuth
import com.kelompoksatuandsatu.preducation.model.auth.UserLogin
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import com.kelompoksatuandsatu.preducation.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createResetPassword(resetPasswordRequest: ResetPasswordRequest): Flow<ResultWrapper<ResetPasswordResponse>>

    suspend fun userRegister(request: UserAuth): Flow<ResultWrapper<String>>

    suspend fun userLogin(request: UserLogin): Flow<ResultWrapper<LoginToken>>
}

class UserRepositoryImpl(private val dataSource: UserDataSource) : UserRepository {
    override suspend fun userRegister(request: UserAuth): Flow<ResultWrapper<String>> {
        return proceedFlow {
            val dataRequest = RegisterRequest(request.email, request.name, request.phone, request.password)
            dataSource.userRegister(dataRequest).message.toString()
        }
    }

    override suspend fun userLogin(request: UserLogin): Flow<ResultWrapper<LoginToken>> {
        return proceedFlow {
            val dataReq = LoginRequest(request.identifier, request.password)
            dataSource.userLogin(dataReq).data.toToken()
        }
    }
    override suspend fun createResetPassword(resetPasswordRequest: ResetPasswordRequest): Flow<ResultWrapper<ResetPasswordResponse>> {
        return proceedFlow { dataSource.createResetPassword(resetPasswordRequest) }
    }
}
