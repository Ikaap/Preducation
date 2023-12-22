package com.kelompoksatuandsatu.preducation.data.network.api.model.auth.register

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("_id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("role")
    val role: String?,
    @SerializedName("accessToken")
    val accessToken: String
)

// fun Data.toRegisterResponse() = UserRegisterResponse(
//    id = this.id.orEmpty(),
//    name = this.name.orEmpty(),
//    phone = this.phone.orEmpty(),
//    role = this.role.orEmpty(),
//    accessToken = this.accessToken.orEmpty()
// )
