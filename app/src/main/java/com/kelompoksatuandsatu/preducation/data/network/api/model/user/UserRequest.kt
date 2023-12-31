package com.kelompoksatuandsatu.preducation.data.network.api.model.user

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

@Keep
data class UserRequest(
    @SerializedName("email")
    val email: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("image_profile")
    val imageProfile: MultipartBody.Part? = null,
    @SerializedName("country")
    val country: String?,
    @SerializedName("city")
    val city: String?
)
