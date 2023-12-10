package com.kelompoksatuandsatu.preducation

import android.app.Application
import com.kelompoksatuandsatu.preducation.di.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(AppModules.modules)
        }
    }

//    companion object {
//        fun createAuthService(): AuthService {
//            val loggingInterceptor = HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BODY
//            }
//
//            val okHttpClient = OkHttpClient.Builder()
//                .addInterceptor(loggingInterceptor)
//                .build()
//
//            val retrofit = Retrofit.Builder()
//                .baseUrl("https://preducation.up.railway.app")
//                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//
//            return retrofit.create(AuthService::class.java)
//        }
//    }
}
