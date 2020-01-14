package com.stacktest.countries

import com.stacktest.domain.countries.CountriesInteractor
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

val countriesModule = module {
    factory { (context: CoroutineContext) ->
        CountriesInteractor(
            context,
            get(),
            10
        )
    }
}