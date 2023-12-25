package com.kelompoksatuandsatu.preducation.utils.exceptions

import com.google.gson.Gson
import com.kelompoksatuandsatu.preducation.data.network.api.model.common.BaseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.DetailCourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.PaymentCourseResponse
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
}
