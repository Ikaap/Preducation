package com.kelompoksatuandsatu.preducation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Register(
    val registerTitle: String,
    val nameLabel: String,
    val emailLabel: String,
    val phoneLabel: String,
    val passwordLabel: String,
    val buttonLabel: String,
    val loginTitle: String,
    val loginText: String
) : Parcelable
