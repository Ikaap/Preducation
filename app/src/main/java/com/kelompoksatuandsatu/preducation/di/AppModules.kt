package com.kelompoksatuandsatu.preducation.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.UserDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.UserDataSourceImpl
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.data.repository.UserRepositoryImpl
import com.kelompoksatuandsatu.preducation.presentation.feature.login.LoginViewModel
import com.kelompoksatuandsatu.preducation.presentation.feature.register.RegisterViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {

    private val localModule = module {
        single { androidContext().userDataStore }
        single<PreferenceDataStoreHelper> { PreferenceDataStoreHelperImpl(get()) }
    }

    private val networkModule = module {
        single { ChuckerInterceptor(androidContext()) }
        single { PreducationService.invoke(get(), get()) }
        single { AuthInterceptor(get()) }
    }

    private val dataSourceModule = module {
        single<UserDataSource> { UserApiDataSource(get()) }
        single<CourseDataSource> { CourseDataSourceImpl(get()) }
        single<UserDataSource> { UserDataSourceImpl(get()) }

    }

    private val repositoryModule = module {
        single<CourseRepository> { CourseRepositoryImpl(get(), get()) }
        single<UserRepository> { UserRepositoryImpl(get(), get()) }
    }

    private val viewModelModule = module {
        viewModelOf(::ResetPasswordViewModel)
        viewModelOf(::HomeViewModel)
        viewModelOf(::SeeAllViewModel)
        viewModelOf(::DetailClassViewModel)
        viewModelOf(::RegisterViewModel)
        viewModelOf(::LoginViewModel)
    }

    val modules: List<Module> = listOf(
        localModule,
        networkModule,
        dataSourceModule,
        repositoryModule,
        viewModelModule
    )
}
