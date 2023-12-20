package com.kelompoksatuandsatu.preducation.model.auth

import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login.Data

data class UserLoginResponse(
    val data: Data,
    val message: String?,
    val success: Boolean
)
