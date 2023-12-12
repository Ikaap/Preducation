package com.kelompoksatuandsatu.preducation.data.network.api.service

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.kelompoksatuandsatu.preducation.BuildConfig
import com.kelompoksatuandsatu.preducation.data.network.api.interceptor.AuthInterceptor
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login.LoginRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login.LoginResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.OtpRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.otp.OtpResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.register.RegisterRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.register.RegisterResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoriesprogress.CategoriesProgressResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoryclass.CategoriesClassResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categorytypeclass.CategoriesTypeClassResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.courseall.CourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.DetailCourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.progress.ProgressCourseRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.progress.ProgressCourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.notification.NotificationResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.PaymentCourseRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.PaymentCourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.progress.courseprogress.CourseProgressResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface PreducationService {

    // home & see all
    @GET("api/v1/categories")
    suspend fun getCategoriesClass() // : CategoriesClassResponse

    @GET("api/v1/courses")
    suspend fun getCourseHome(@Query("category") category: String? = null) // : CourseResponse

    // post otp
    @POST("api/v1/auths/email-otp")
    suspend fun getOtpToEmail(@Body email: OtpRequest): OtpResponse

    // notification
    @GET("api/v1/notifications")
    suspend fun getUserNotification(): NotificationResponse

    // class
    // get all categories class
    @GET("api/v1/categories/progress")
    suspend fun getCategoriesProgress(): CategoriesProgressResponse

    // get all categories progress class
    @GET("api/v1/progress")
    suspend fun getCourseUserProgress(@Query("progressClass") progressClass: String? = null): CourseProgressResponse

    // course
    @GET("api/v1/courses")
    suspend fun getCourseTopic(@Query("typeClass") typeClass: String? = null) // : CourseResponse

    // get all categories type class
    @GET("api/v1/categories/type-class")
    suspend fun getCategoriesTypeClass(@Query("typeClass") typeClass: String? = null): CategoriesTypeClassResponse

    // detail
    @GET("api/v1/courses/{id}")
    suspend fun getCourseById(@Path("id") id: String): DetailCourseResponse

    @POST("api/v1/progress")
    suspend fun postIndexCourseById(@Query("courseId") id: String, @Body progressRequest: ProgressCourseRequest): ProgressCourseResponse

    // profile
    @GET("api/v1/users/{id}")
    suspend fun getUserById(@Path("id") id: String? = null): UserResponse

    @PATCH("api/v1/users/{id}")
    suspend fun updateUserById(@Path("id") id: String? = null, @Body userRequest: UserRequest): UserResponse

    @PATCH("api/v1/users/update-password/{id}")
    suspend fun updateUserPassword(@Path("id") id: String? = null, @Body changePasswordRequest: ChangePasswordRequest): ChangePasswordResponse

    @GET("api/v1/payments")
    suspend fun getHistoryPayment(): HistoryPaymentResponse

    //     auth
    @POST("api/v1/auths/register")
    suspend fun userRegister(@Body userRegisterRequest: RegisterRequest): RegisterResponse

    @POST("api/v1/auths/login")
    suspend fun userLogin(@Body userLoginRequest: LoginRequest): LoginResponse

    @DELETE("api/v1/auths/logout")
    suspend fun logout(): UserLogoutResponse

    // @POST("api/v1/auths/forgot-password")
    // suspend fun userForgotPassword(@Body userForgotPassword: UserForgotPasswordRequest)//:UserForgotPasswordResponse
    // @POST("api/v1/auths/email-otp")
    // suspend fun userPostOtp(@Body userForgotPassword: UserForgotPasswordRequest)//:UserForgotPasswordResponse

    // payment
    @POST("api/v1/payments")
    suspend fun paymentCourse(@Body paymentCourseRequest: PaymentCourseRequest): PaymentCourseResponse

    companion object {
        @JvmStatic
        operator fun invoke(chucker: ChuckerInterceptor, authInterceptor: AuthInterceptor): PreducationService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(chucker)
                .addInterceptor(authInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(PreducationService::class.java)
        }
    }
}
