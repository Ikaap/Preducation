package com.kelompoksatuandsatu.preducation.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.CourseDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.CourseDataSourceImpl
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepository
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepositoryImpl
import com.kelompoksatuandsatu.preducation.presentation.feature.home.HomeViewModel
import com.kelompoksatuandsatu.preducation.presentation.feature.home.SeeAllViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.UserApiDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.UserDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.data.repository.UserRepositoryImpl
import com.kelompoksatuandsatu.preducation.presentation.feature.resetpassword.ResetPasswordViewModel
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
        single<UserDataSource> { UserApiDataSource(get()) }
        single<CourseDataSource> { CourseDataSourceImpl(get()) }

    }

    private val repositoryModule = module {
        single<UserRepository> { UserRepositoryImpl(get()) }
        single<CourseRepository> { CourseRepositoryImpl(get()) }

    }

    private val viewModelModule = module {
        viewModelOf(::ResetPasswordViewModel)
        viewModelOf(::HomeViewModel)
        viewModelOf(::SeeAllViewModel)

    }

    val modules: List<Module> = listOf(
        networkModule,
        dataSourceModule,
        repositoryModule,
        viewModelModule
    )
}
