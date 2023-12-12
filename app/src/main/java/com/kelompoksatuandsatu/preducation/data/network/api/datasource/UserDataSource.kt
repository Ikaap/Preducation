package com.kelompoksatuandsatu.preducation.data.network.api.datasource

import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login.LoginRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login.LoginResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.OtpRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.OtpResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.register.RegisterRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.register.RegisterResponse
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService
import com.kelompoksatuandsatu.preducation.data.network.api.model.changepassword.ChangePasswordRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.changepassword.ChangePasswordResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.logout.UserLogoutResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.UserRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.UserResponse
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService


interface UserDataSource {
    suspend fun userRegister(userRegisterRequest: RegisterRequest): RegisterResponse

    suspend fun userLogin(userLoginRequest: LoginRequest): LoginResponse

    suspend fun getUserById(id: String? = null): UserResponse
    suspend fun userOtp(userOtpRequest: OtpRequest): OtpResponse

    suspend fun getUserById(): UserResponse
    suspend fun updateUserById(id: String, userRequest: UserRequest): UserResponse
    suspend fun updateUserPassword(id: String, passwordRequest: ChangePasswordRequest): ChangePasswordResponse
    suspend fun performLogout(): UserLogoutResponse

}

class UserDataSourceImpl(private val service: PreducationService) : UserDataSource {
    override suspend fun userRegister(userRegisterRequest: RegisterRequest): RegisterResponse {
        return service.userRegister(userRegisterRequest)
    }

    override suspend fun userLogin(userLoginRequest: LoginRequest): LoginResponse {
        return service.userLogin(userLoginRequest)
    }
    override suspend fun getUserById(id: String?): UserResponse {
        return service.getUserById(id)

    override suspend fun userOtp(userOtpRequest: OtpRequest): OtpResponse {
        return service.getOtpToEmail(userOtpRequest)
    }

    override suspend fun getUserById(): UserResponse {
        return service.getUserById()
    }

    override suspend fun updateUserById(id: String, userRequest: UserRequest): UserResponse {
        return service.updateUserById(id, userRequest)
    }

    override suspend fun updateUserPassword(id: String, passwordRequest: ChangePasswordRequest): ChangePasswordResponse {
        return service.updateUserPassword(id, passwordRequest)
    }

    override suspend fun performLogout(): UserLogoutResponse {
        return service.logout()
    }
}
