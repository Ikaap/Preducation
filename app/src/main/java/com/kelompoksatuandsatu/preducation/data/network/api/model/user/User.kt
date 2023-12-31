package com.kelompoksatuandsatu.preducation.data.network.api.model.user

import com.google.gson.annotations.SerializedName
import com.kelompoksatuandsatu.preducation.model.user.UserViewParam

data class User(
    @SerializedName("_id")
    val id: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("image_profile")
    val imageProfile: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("city")
    val city: String?
)

fun User.toUserViewParam() = UserViewParam(
    id = this.id.orEmpty(),
    email = this.email.orEmpty(),
    phone = this.phone.orEmpty(),
    name = this.name.orEmpty(),
    imageProfile = this.imageProfile.orEmpty(),
    country = this.country.orEmpty(),
    city = this.city.orEmpty()
)
