package com.kelompoksatuandsatu.preducation.network

import com.kelompoksatuandsatu.preducation.model.APIResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {
    @FormUrlEncoded
    @POST("/api/v1/auths/login")
    fun login(
        @Field("identifier") identifier: String,
        @Field("password") password: String
    ): Call<APIResponse>
}
