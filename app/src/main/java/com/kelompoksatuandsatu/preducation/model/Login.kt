package com.kelompoksatuandsatu.preducation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Login(
    val loginTitle: String,
    val emailLabel: String,
    val passwordLabel: String,
    val forgotLabel: String,
    val buttonLabel: String,
    val registerTitle: String,
    val registerText: String,
    val withoutText: String
) : Parcelable
