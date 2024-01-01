package com.kelompoksatuandsatu.preducation.data.network.api.datasource

import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.forgotpassword.ForgotPasswordRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.forgotpassword.ForgotPasswordResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login.LoginRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login.LoginResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.logout.LogoutResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.postemail.EmailOtpRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.postemail.EmailOtpResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.verifyotp.OtpRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.register.RegisterRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.register.RegisterResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.UserResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.changepassword.ChangePasswordRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.changepassword.ChangePasswordResponse
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface UserDataSource {
    suspend fun userRegister(userRegisterRequest: RegisterRequest): RegisterResponse

    suspend fun userLogin(userLoginRequest: LoginRequest): LoginResponse

    suspend fun postEmailOtp(emailOtpRequest: EmailOtpRequest): EmailOtpResponse

    suspend fun verifyOtp(otpRequest: OtpRequest): com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.verifyotp.OtpResponse

    suspend fun getUserById(id: String): UserResponse

    suspend fun updateUserById(
        id: String,
        name: RequestBody?,
        email: RequestBody?,
        phone: RequestBody?,
        country: RequestBody?,
        city: RequestBody?,
        imageProfile: MultipartBody.Part?
    ): UserResponse

    suspend fun updateUserPassword(
        id: String,
        passwordRequest: ChangePasswordRequest
    ): ChangePasswordResponse

    suspend fun userLogout(): Response<LogoutResponse>
    suspend fun userForgotPassword(forgotPasswordRequest: ForgotPasswordRequest): ForgotPasswordResponse
}

class UserDataSourceImpl(private val service: PreducationService) : UserDataSource {
    override suspend fun userRegister(userRegisterRequest: RegisterRequest): RegisterResponse {
        return service.userRegister(userRegisterRequest)
    }

    override suspend fun userLogin(userLoginRequest: LoginRequest): LoginResponse {
        return service.userLogin(userLoginRequest)
    }

    override suspend fun getUserById(id: String): UserResponse {
        return service.getUserById(id)
    }

    override suspend fun postEmailOtp(emailOtpRequest: EmailOtpRequest): EmailOtpResponse {
        return service.postEmailOtp(emailOtpRequest)
    }

    override suspend fun verifyOtp(otpRequest: OtpRequest): com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.verifyotp.OtpResponse {
        return service.verifyOtp(otpRequest)
    }

    override suspend fun updateUserById(
        id: String,
        name: RequestBody?,
        email: RequestBody?,
        phone: RequestBody?,
        country: RequestBody?,
        city: RequestBody?,
        imageProfile: MultipartBody.Part?
    ): UserResponse {
        return service.updateUserById(id, name, email, phone, country, city, imageProfile)
    }

    override suspend fun updateUserPassword(
        id: String,
        passwordRequest: ChangePasswordRequest
    ): ChangePasswordResponse {
        return service.updateUserPassword(id, passwordRequest)
    }

    override suspend fun userForgotPassword(forgotPasswordRequest: ForgotPasswordRequest): ForgotPasswordResponse {
        return service.userForgotPassword(forgotPasswordRequest)
    }

    override suspend fun userLogout(): Response<LogoutResponse> {
        return service.userLogout()
    }
}
