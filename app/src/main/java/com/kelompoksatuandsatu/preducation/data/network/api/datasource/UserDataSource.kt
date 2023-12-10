package com.kelompoksatuandsatu.preducation.data.network.api.datasource

import com.kelompoksatuandsatu.preducation.data.network.api.model.ResetPasswordRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.ResetPasswordResponse
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService

interface UserDataSource {
    suspend fun createResetPassword(resetPasswordRequest: ResetPasswordRequest): ResetPasswordResponse

    suspend fun userRegister(userRegisterRequest: RegisterRequest): RegisterResponse

    suspend fun userLogin(userLoginRequest: LoginRequest): LoginResponse

}
class UserApiDataSource(private val service: PreducationService) : UserDataSource {
    override suspend fun createResetPassword(resetPasswordRequest: ResetPasswordRequest): ResetPasswordResponse {
        return service.createResetPassword(resetPasswordRequest)
    }
    override suspend fun userRegister(userRegisterRequest: RegisterRequest): RegisterResponse {
        return service.userRegister(userRegisterRequest)
    }

    override suspend fun userLogin(userLoginRequest: LoginRequest): LoginResponse {
        return service.userLogin(userLoginRequest)
    }
}

