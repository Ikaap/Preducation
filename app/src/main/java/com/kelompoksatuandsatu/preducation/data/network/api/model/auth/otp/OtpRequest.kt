package com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OtpRequest(
	@SerializedName("email")
	val email: String?
)
