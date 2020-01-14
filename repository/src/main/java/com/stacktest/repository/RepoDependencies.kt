package com.stacktest.repository

import android.util.Log
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.stacktest.domain.countries.CountriesRepository
import com.stacktest.domain.regions.RegionsRepository
import com.stacktest.repository.countries.CountriesDataSource
import com.stacktest.repository.countries.CountriesRepositoryImpl
import com.stacktest.repository.countries.CountriesStorage
import com.stacktest.repository.countries.network.RetrofitCountriesApi
import com.stacktest.repository.countries.network.RetrofitCountriesDataSourceImpl
import com.stacktest.repository.countries.network.RetrofitRegionsApi
import com.stacktest.repository.countries.network.RetrofitRegionsDataSourceImpl
import com.stacktest.repository.countries.persistence.CountriesStorageImpl
import com.stacktest.repository.countries.persistence.CountryDao
import com.stacktest.repository.regions.RegionsDataSource
import com.stacktest.repository.regions.RegionsRepositoryImpl
import com.stacktest.repository.regions.RegionsStorage
import com.stacktest.repository.regions.persistence.RegionDao
import com.stacktest.repository.regions.persistence.RegionsStorageImpl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val reposModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(get(), AppDatabase::class.java, "stacktest").build()
    }

    single<Retrofit> {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor {
            val request: Request = it.request()
            Log.d("REQUEST", "request: $request")
            it.proceed(request)
        }

        Retrofit.Builder()
            .baseUrl("https://api.vk.com/method/")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    //regions
    single<RegionsRepository> {
        RegionsRepositoryImpl(get(), get())
    }
    factory<RegionDao> {
        get<AppDatabase>().regionDao()
    }
    factory<RegionsStorage> {
        RegionsStorageImpl(get())
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
    factory<CountryDao> {
        get<AppDatabase>().countryDao()
    }
    factory<CountriesStorage> {
        CountriesStorageImpl(get())
    }
    factory<CountriesDataSource> {
        RetrofitCountriesDataSourceImpl(get())
    }
    factory<RetrofitCountriesApi> {
        get<Retrofit>().create(RetrofitCountriesApi::class.java)
    }

}