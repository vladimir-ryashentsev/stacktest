package com.stacktest

import android.app.Application
import com.androidjunior.coroutinestest.presentation.repos.details.regionsModule
import com.stacktest.countries.countriesModule
import com.stacktest.repository.reposModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CountriesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CountriesApp)
            modules(
                listOf(
                    reposModule,
                    countriesModule,
                    regionsModule
                )
            )
        }
    }
}