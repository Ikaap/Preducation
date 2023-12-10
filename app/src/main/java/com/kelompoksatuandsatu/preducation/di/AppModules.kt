package com.kelompoksatuandsatu.preducation.di

import com.kelompoksatuandsatu.preducation.App.Companion.createAuthService
import com.kelompoksatuandsatu.preducation.network.AuthService
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {
    val modules: List<Module> = listOf(
        module {
            single<AuthService> { createAuthService() }
        }
    )
}
