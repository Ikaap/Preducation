package com.kelompoksatuandsatu.preducation.data.network.api.datasource

import com.kelompoksatuandsatu.preducation.data.network.api.model.ResetPasswordRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.ResetPasswordResponse
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService

interface UserDataSource {
    suspend fun createResetPassword(resetPasswordRequest: ResetPasswordRequest): ResetPasswordResponse
}
class UserApiDataSource(private val service: PreducationService) : UserDataSource {
    override suspend fun createResetPassword(resetPasswordRequest: ResetPasswordRequest): ResetPasswordResponse {
        return service.createResetPassword(resetPasswordRequest)
    }
}
