package com.kelompoksatuandsatu.preducation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserItem(
    val email: String,
    val phone: String,
    val name: String,
    val country: String,
    val city: String
) : Parcelable
