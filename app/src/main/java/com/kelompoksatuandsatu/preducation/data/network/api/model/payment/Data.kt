package com.kelompoksatuandsatu.preducation.data.network.api.model.payment

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("redirect_url")
    val redirectUrl: String?,
    @SerializedName("token")
    val token: String?
)

// fun Data.toPaymentResponse() = PaymentResponseViewParam(
//    redirectUrl = this.redirectUrl.orEmpty(),
//    token = this.token.orEmpty()
// )
