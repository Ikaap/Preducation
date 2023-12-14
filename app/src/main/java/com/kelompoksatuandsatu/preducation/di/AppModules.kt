package com.kelompoksatuandsatu.preducation.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.kelompoksatuandsatu.preducation.data.local.datastore.appDataStore
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSourceImpl
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.CourseDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.CourseDataSourceImpl
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.UserDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.UserDataSourceImpl
import com.kelompoksatuandsatu.preducation.data.network.api.interceptor.AuthInterceptor
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepository
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepositoryImpl
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.data.repository.UserRepositoryImpl
import com.kelompoksatuandsatu.preducation.presentation.feature.classProgress.ProgressClassViewModel
import com.kelompoksatuandsatu.preducation.presentation.feature.course.CourseViewModel
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.DetailClassViewModel
import com.kelompoksatuandsatu.preducation.presentation.feature.home.HomeViewModel
import com.kelompoksatuandsatu.preducation.presentation.feature.login.LoginViewModel
import com.kelompoksatuandsatu.preducation.presentation.feature.payment.PaymentViewModel
import com.kelompoksatuandsatu.preducation.presentation.feature.register.RegisterViewModel
import com.kelompoksatuandsatu.preducation.utils.AssetWrapper
import com.kelompoksatuandsatu.preducation.utils.PreferenceDataStoreHelper
import com.kelompoksatuandsatu.preducation.utils.PreferenceDataStoreHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
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
        single { PreducationService.invoke(get(), get()) }
        single { AuthInterceptor(get()) }
    }

    private val dataSourceModule = module {
        single<UserPreferenceDataSource> { UserPreferenceDataSourceImpl(get()) }
        single<CourseDataSource> { CourseDataSourceImpl(get()) }
        single<UserDataSource> { UserDataSourceImpl(get()) }
    }

    private val repositoryModule = module {
        single<CourseRepository> { CourseRepositoryImpl(get()) }
        single<UserRepository> { UserRepositoryImpl(get(), get()) }
    }

    private val viewModelModule = module {
        viewModelOf(::HomeViewModel)
        viewModelOf(::DetailClassViewModel)
        viewModelOf(::CourseViewModel)
        viewModel { param -> PaymentViewModel(param.get(), get()) }
//        viewModel { param -> DetailClassViewModel(param.get(), get(), get()) }
        viewModelOf(::RegisterViewModel)
        viewModelOf(::LoginViewModel)
        viewModelOf(::ProgressClassViewModel)
    }

    private val utilsModule = module {
        single { AssetWrapper(androidContext()) }
    }

    val modules: List<Module> = listOf(
        localModule,
        networkModule,
        dataSourceModule,
        repositoryModule,
        viewModelModule,
        utilsModule
    )
}
