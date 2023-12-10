package com.kelompoksatuandsatu.preducation.network

<<<<<<< HEAD
import com.kelompoksatuandsatu.preducation.model.LoginResponse
import com.kelompoksatuandsatu.preducation.model.RegisterResponse
=======
import com.kelompoksatuandsatu.preducation.model.APIResponse
>>>>>>> eba8e9e (Consume auths Login API)
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
<<<<<<< HEAD
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("/api/v1/auths/register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("password") password: String
    ): Call<RegisterResponse>
=======
    ): Call<APIResponse>
>>>>>>> eba8e9e (Consume auths Login API)
}
