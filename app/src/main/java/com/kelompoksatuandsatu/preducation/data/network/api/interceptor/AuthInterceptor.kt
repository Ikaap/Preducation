package com.kelompoksatuandsatu.preducation.data.network.api.interceptor

import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val preference: UserPreferenceDataSource) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        runBlocking {
            val token = preference.getUserToken()
            if (token.isNotEmpty()) {
                request = request.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            } else {
                request = request.newBuilder()
                    .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NTc5YTI3MzQ3NjEwMDFmOWU0OGRmNGEiLCJlbWFpbCI6InVzZXIxQGV4YW1wbGUuY29tIiwicGhvbmUiOiIxMjM0NTY3ODkwIiwicm9sZSI6InVzZXIiLCJpYXQiOjE3MDI3MjIxNDUsImV4cCI6MTcwMjk4MTM0NX0.IqvbBr2aZbvJn0qxPap_C27rW-bW6U5E964ksypuU1E")
                    .build()
            }
        }
        return chain.proceed(request)
    }
}
