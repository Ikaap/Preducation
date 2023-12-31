package com.kelompoksatuandsatu.preducation.utils.exceptions

import com.google.gson.Gson
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.forgotpassword.ForgotPasswordResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login.LoginResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.register.RegisterResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoryclass.CategoriesClassResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categorytypeclass.CategoriesTypeClassResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.common.BaseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.courseall.CourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.DetailCourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.progress.ProgressCourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.notification.NotificationResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.PaymentCourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history.HistoryItemResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.UserResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.changepassword.ChangePasswordResponse
import retrofit2.Response

class ApiException(
    override val message: String?,
    val httpCode: Int,
    val errorResponse: Response<*>?
) : Exception() {

    fun getParsedError(): BaseResponse? {
        val body = errorResponse?.errorBody()?.string().orEmpty()
        return try {
            val bodyObj = Gson().fromJson(body, BaseResponse::class.java)
            bodyObj
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getParsedErrorPayment(): PaymentCourseResponse? {
        val body = errorResponse?.errorBody()?.string().orEmpty()
        return try {
            val bodyObj = Gson().fromJson(body, PaymentCourseResponse::class.java)
            bodyObj
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getParsedErrorDetailClass(): DetailCourseResponse? {
        val body = errorResponse?.errorBody()?.string().orEmpty()
        return try {
            val bodyObj = Gson().fromJson(body, DetailCourseResponse::class.java)
            bodyObj
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun getParsedErrorProgressClass(): ProgressCourseResponse? {
        val body = errorResponse?.errorBody()?.string().orEmpty()
        return try {
            val bodyObj = Gson().fromJson(body, ProgressCourseResponse::class.java)
            bodyObj
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun getParsedErrorCourse(): CourseResponse? {
        val body = errorResponse?.errorBody()?.string().orEmpty()
        return try {
            val bodyObj = Gson().fromJson(body, CourseResponse::class.java)
            bodyObj
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun getParsedErrorCategories(): CategoriesClassResponse? {
        val body = errorResponse?.errorBody()?.string().orEmpty()
        return try {
            val bodyObj = Gson().fromJson(body, CategoriesClassResponse::class.java)
            bodyObj
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getParsedErrorCategoriesType(): CategoriesTypeClassResponse? {
        val body = errorResponse?.errorBody()?.string().orEmpty()
        return try {
            val bodyObj = Gson().fromJson(body, CategoriesTypeClassResponse::class.java)
            bodyObj
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun getParsedErrorForgetPassword(): ForgotPasswordResponse? {
        val body = errorResponse?.errorBody()?.string().orEmpty()
        return try {
            val bodyObj = Gson().fromJson(body, ForgotPasswordResponse::class.java)
            bodyObj
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun getParsedErrorProfile(): UserResponse? {
        val body = errorResponse?.errorBody()?.string().orEmpty()
        return try {
            val bodyObj = Gson().fromJson(body, UserResponse::class.java)
            bodyObj
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun getParsedErrorChangePassword(): ChangePasswordResponse? {
        val body = errorResponse?.errorBody()?.string().orEmpty()
        return try {
            val bodyObj = Gson().fromJson(body, ChangePasswordResponse::class.java)
            bodyObj
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun getParsedErrorHistoryPayment(): HistoryItemResponse? {
        val body = errorResponse?.errorBody()?.string().orEmpty()
        return try {
            val bodyObj = Gson().fromJson(body, HistoryItemResponse::class.java)
            bodyObj
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun getParsedErrorLogin(): LoginResponse? {
        val body = errorResponse?.errorBody()?.string().orEmpty()
        return try {
            val bodyObj = Gson().fromJson(body, LoginResponse::class.java)
            bodyObj
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun getParsedErrorResister(): RegisterResponse? {
        val body = errorResponse?.errorBody()?.string().orEmpty()
        return try {
            val bodyObj = Gson().fromJson(body, RegisterResponse::class.java)
            bodyObj
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun getParsedErrorNotifications(): NotificationResponse? {
        val body = errorResponse?.errorBody()?.string().orEmpty()
        return try {
            val bodyObj = Gson().fromJson(body, NotificationResponse::class.java)
            bodyObj
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
