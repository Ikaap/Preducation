package com.kelompoksatuandsatu.preducation.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserViewParam(
    val id: String?,
    val email: String?,
    val phone: String,
    val name: String,
    val username: String,
    val imageProfile: String,
    val country: String,
    val city: String
) : Parcelable
