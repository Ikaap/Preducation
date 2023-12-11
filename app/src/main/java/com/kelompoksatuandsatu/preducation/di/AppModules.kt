package com.kelompoksatuandsatu.preducation.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.CourseDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.CourseDataSourceImpl
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepository
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepositoryImpl
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.DetailClassViewModel
import com.kelompoksatuandsatu.preducation.presentation.feature.home.HomeViewModel
import com.kelompoksatuandsatu.preducation.presentation.feature.home.SeeAllViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {

    private val localModule = module {
        single { androidContext().appDataStore }
        single<PreferenceDataStoreHelper> { PreferenceDataStoreHelperImpl(get()) }
    }

    private val networkModule = module {
        single { ChuckerInterceptor(androidContext()) }
        single { PreducationService.invoke(get(),get()) }
        single { AuthInterceptor(get()) }
    }

    private val dataSourceModule = module {
        single<UserPreferenceDataSource> { UserPreferenceDataSourceImpl(get()) }
        single<CourseDataSource> { CourseDataSourceImpl(get(), get()) }
        single<UserDataSource> { UserDataSourceImpl(get()) }
    }

    private val repositoryModule = module {
        single<CourseRepository> { CourseRepositoryImpl(get(), get()) }
        single<UserRepository> { UserRepositoryImpl(get(), get()) }
    }

    private val viewModelModule = module {
        viewModelOf(::ProgressClassViewModel)
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
