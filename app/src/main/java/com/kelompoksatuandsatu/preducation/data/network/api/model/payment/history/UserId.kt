package com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserId(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("image_profile")
    val imageProfile: String?,
    @SerializedName("isActive")
    val isActive: Boolean?,
    @SerializedName("isVerify")
    val isVerify: Boolean?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("role")
    val role: String?,
    @SerializedName("username")
    val username: String?
)
