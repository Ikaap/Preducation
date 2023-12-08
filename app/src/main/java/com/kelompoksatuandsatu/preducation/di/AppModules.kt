package com.kelompoksatuandsatu.preducation.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.NotificationApiDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.NotificationDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService
import com.kelompoksatuandsatu.preducation.data.repository.NotificationRepository
import com.kelompoksatuandsatu.preducation.data.repository.NotificationRepositoryImpl
import com.kelompoksatuandsatu.preducation.presentation.feature.notifications.NotificationViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {

    private val networkModule = module {
        single { ChuckerInterceptor(androidContext()) }
        single { PreducationService.invoke(get()) }
    }
    private val dataSourceModule = module {
        single<NotificationDataSource> { NotificationApiDataSource(get()) }
    }

    private val repositoryModule = module {
        single<NotificationRepository> { NotificationRepositoryImpl(get()) }
    }

    private val viewModelModule = module {
        viewModelOf(::NotificationViewModel)
    }

    val modules: List<Module> = listOf(
        networkModule,
        dataSourceModule,
        repositoryModule,
        viewModelModule
    )
}
