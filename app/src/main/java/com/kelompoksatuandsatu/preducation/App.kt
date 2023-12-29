package com.kelompoksatuandsatu.preducation

import android.app.Application
import android.content.res.Configuration
import com.kelompoksatuandsatu.preducation.di.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.Locale

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
        setAppLocale("in")
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(AppModules.modules)
        }
    }

    private fun setAppLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }
}
