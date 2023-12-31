package com.kelompoksatuandsatu.preducation.data.network.api.model.user.changepassword

import com.google.gson.annotations.SerializedName
import com.kelompoksatuandsatu.preducation.model.user.Password

data class ChangePasswordItem(
    @SerializedName("_id")
    val id: String,
    @SerializedName("oldPassword")
    val oldPassword: String,
    @SerializedName("newPassword")
    val newPassword: String,
    @SerializedName("confirmPassword")
    val confirmPassword: String
)

fun ChangePasswordItem.toChangePasswordRequest() = Password(
    id = id,
    oldPassword = oldPassword,
    newPassword = newPassword,
    confirmPassword = confirmPassword
)

fun Collection<ChangePasswordItem>.toPasswordList() = this.map { it.toChangePasswordRequest() }
