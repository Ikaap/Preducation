package com.kelompoksatuandsatu.preducation.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.UserDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.UserDataSourceImpl
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepository
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepositoryImpl
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.DetailClassViewModel
import com.kelompoksatuandsatu.preducation.presentation.feature.home.HomeViewModel
import com.kelompoksatuandsatu.preducation.presentation.feature.home.SeeAllViewModel
import com.kelompoksatuandsatu.preducation.presentation.feature.payment.PaymentViewModel
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.data.repository.UserRepositoryImpl
import com.kelompoksatuandsatu.preducation.presentation.feature.login.LoginViewModel
import com.kelompoksatuandsatu.preducation.presentation.feature.register.RegisterViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {
    private val networkModule = module {
        single { ChuckerInterceptor(androidContext()) }
        single { PreducationService.invoke(get()) }
    }

    private val dataSourceModule = module {
        single<CourseDataSource> { CourseDataSourceImpl(get()) }
        single<UserDataSource> { UserDataSourceImpl(get()) }

    }

    private val repositoryModule = module {
        single<CourseRepository> { CourseRepositoryImpl(get()) }
        single<UserRepository> { UserRepositoryImpl(get()) }

    }

    private val viewModelModule = module {
        viewModelOf(::HomeViewModel)
        viewModelOf(::SeeAllViewModel)
        viewModelOf(::DetailClassViewModel)
        viewModel { param -> PaymentViewModel(param.get(), get()) }
        viewModelOf(::RegisterViewModel)
        viewModelOf(::LoginViewModel)
    }

    val modules: List<Module> = listOf(
        networkModule,
        dataSourceModule,
        repositoryModule,
        viewModelModule
    )
}
