package com.stacktest.repository.components

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.stacktest.domain.countries.CountriesRepository
import com.stacktest.domain.regions.RegionsRepository
import com.stacktest.repository.components.countries.InMemoryCountriesStorage
import com.stacktest.repository.components.regions.InMemoryRegionsStorage
import com.stacktest.repository.countries.CountriesDataSource
import com.stacktest.repository.countries.CountriesRepositoryImpl
import com.stacktest.repository.countries.CountriesStorage
import com.stacktest.repository.countries.network.RetrofitCountriesApi
import com.stacktest.repository.countries.network.RetrofitCountriesDataSourceImpl
import com.stacktest.repository.countries.network.RetrofitRegionsApi
import com.stacktest.repository.countries.network.RetrofitRegionsDataSourceImpl
import com.stacktest.repository.regions.RegionsDataSource
import com.stacktest.repository.regions.RegionsRepositoryImpl
import com.stacktest.repository.regions.RegionsStorage
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.KoinComponent
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val testReposModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://api.vk.com/method/")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //regions
    single<RegionsRepository> {
        RegionsRepositoryImpl(get(), get())
    }
    factory<RegionsStorage> {
        InMemoryRegionsStorage()
    }
    factory<RegionsDataSource> {
        RetrofitRegionsDataSourceImpl(get())
    }
    factory<RetrofitRegionsApi> {
        get<Retrofit>().create(RetrofitRegionsApi::class.java)
    }

    //countries
    single<CountriesRepository> {
        CountriesRepositoryImpl(get(), get())
    }
    factory<CountriesStorage> {
        InMemoryCountriesStorage()
    }
    factory<CountriesDataSource> {
        RetrofitCountriesDataSourceImpl(get())
    }
    factory<RetrofitCountriesApi> {
        get<Retrofit>().create(RetrofitCountriesApi::class.java)
    }

}

val reposKoinApp = koinApplication {
    modules(testReposModule)
}

object koinContext {
    var koinApp: KoinApplication =
        reposKoinApp
}

abstract class RepositoriesKoinComponent : KoinComponent {
    override fun getKoin(): Koin = koinContext.koinApp.koin
}