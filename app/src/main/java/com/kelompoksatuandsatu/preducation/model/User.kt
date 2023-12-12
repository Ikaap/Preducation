package com.kelompoksatuandsatu.preducation.model

import android.os.Parcelable
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.UserRequest
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val email: String,
    val phone: String,
    val name: String,
    val username: String,
    val imageProfile: String,
    val country: String,
    val city: String,
    val role: String
) : Parcelable

fun User.toUserRequest() = UserRequest(
    email = email,
    phone = phone,
    name = name,
    imageProfile = imageProfile,
    country = country,
    city = city
)
