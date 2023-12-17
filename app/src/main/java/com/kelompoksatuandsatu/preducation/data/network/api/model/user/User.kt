package com.kelompoksatuandsatu.preducation.data.network.api.model.user

import com.google.gson.annotations.SerializedName
import com.kelompoksatuandsatu.preducation.model.UserViewParam

data class User(
    @SerializedName("_id")
    val id: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("image_profile")
    val imageProfile: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("city")
    val city: String
)

fun User.toUserRequest() = UserViewParam(
    id = id,
    email = email,
    phone = phone,
    name = name,
    username = username,
    imageProfile = imageProfile,
    country = country,
    city = city
)

fun Collection<User>.toUserList() = this.map { it.toUserRequest() }
