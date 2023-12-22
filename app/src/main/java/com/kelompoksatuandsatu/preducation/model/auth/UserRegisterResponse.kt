package com.kelompoksatuandsatu.preducation.model.auth

import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.register.Data

data class UserRegisterResponse(
    val data: Data,
    val message: String?,
    val success: Boolean
)
