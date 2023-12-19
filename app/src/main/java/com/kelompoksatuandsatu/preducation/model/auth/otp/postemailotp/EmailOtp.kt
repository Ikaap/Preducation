package com.kelompoksatuandsatu.preducation.model.auth.otp.postemailotp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EmailOtp(
    val email: String?
) : Parcelable
