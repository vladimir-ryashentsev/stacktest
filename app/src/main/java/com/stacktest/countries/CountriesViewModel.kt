package com.stacktest.countries

import androidx.annotation.MainThread
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stacktest.domain.base.State
import com.stacktest.domain.countries.CountriesInteractor
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import androidx.lifecycle.Transformations.distinctUntilChanged as untilChanged

class CountriesViewModel : ViewModel(), KoinComponent {

    private val interactor: CountriesInteractor by inject { parametersOf(viewModelScope.coroutineContext) }

    val isError = untilChanged(map(interactor.getState()) { it is State.Error })
    val error =
        untilChanged(map(interactor.getState()) { if (it is State.Error) it.message else "" })
    val isCompletelyLoaded = untilChanged(interactor.isCompletelyLoaded())
    val countries = interactor.getData()

    @MainThread
    fun requestNewPage() = interactor.doActionAsync()

    init {
        interactor.doActionAsync()
    }
}