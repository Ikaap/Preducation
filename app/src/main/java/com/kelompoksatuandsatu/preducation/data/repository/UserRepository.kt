package com.kelompoksatuandsatu.preducation.data.repository

import com.kelompoksatuandsatu.preducation.data.network.api.datasource.UserDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.ResetPasswordRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.ResetPasswordResponse
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import com.kelompoksatuandsatu.preducation.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createResetPassword(resetPasswordRequest: ResetPasswordRequest): Flow<ResultWrapper<ResetPasswordResponse>>
}

class UserRepositoryImpl(private val dataSource: UserDataSource) : UserRepository {
    override suspend fun createResetPassword(resetPasswordRequest: ResetPasswordRequest): Flow<ResultWrapper<ResetPasswordResponse>> {
        return proceedFlow { dataSource.createResetPassword(resetPasswordRequest) }
    }
}
