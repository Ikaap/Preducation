package com.kelompoksatuandsatu.preducation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Password(
    val id: String,
    val oldPassword: String,
    val newPassword: String,
    val confirmPassword: String
) : Parcelable
