package com.androidjunior.coroutinestest.presentation.repos.details

import com.stacktest.domain.regions.RegionsInteractor
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

val regionsModule = module {
    factory { (context: CoroutineContext) ->
        RegionsInteractor(
            context,
            get(),
            10
        )
    }
}