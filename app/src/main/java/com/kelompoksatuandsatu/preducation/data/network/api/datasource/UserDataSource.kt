package com.kelompoksatuandsatu.preducation.data.network.api.datasource

import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login.LoginRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login.LoginResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.OtpRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.OtpResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.register.RegisterRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.register.RegisterResponse
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService

interface UserDataSource {
    suspend fun userRegister(userRegisterRequest: RegisterRequest): RegisterResponse

    suspend fun userLogin(userLoginRequest: LoginRequest): LoginResponse

    suspend fun userOtp(userOtpRequest: OtpRequest): OtpResponse
}

class UserDataSourceImpl(private val service: PreducationService) : UserDataSource {
    override suspend fun userRegister(userRegisterRequest: RegisterRequest): RegisterResponse {
        return service.userRegister(userRegisterRequest)
    }

    override suspend fun userLogin(userLoginRequest: LoginRequest): LoginResponse {
        return service.userLogin(userLoginRequest)
    }

    override suspend fun userOtp(userOtpRequest: OtpRequest): OtpResponse {
        return service.getOtpToEmail(userOtpRequest)
    }
}
