package com.stacktest.regions

import androidx.annotation.MainThread
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stacktest.domain.base.State
import com.stacktest.domain.regions.RegionsInteractor
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import androidx.lifecycle.Transformations.distinctUntilChanged as untilChanged

class RegionsViewModel : ViewModel(), KoinComponent {

    private val interactor: RegionsInteractor by inject { parametersOf(viewModelScope.coroutineContext) }

    val isError = untilChanged(map(interactor.getState()) { it is State.Error })
    val error =
        untilChanged(map(interactor.getState()) { if (it is State.Error) it.message else "" })
    val isCompletelyLoaded = untilChanged(interactor.isCompletelyLoaded())
    val regions = interactor.getData()

    var countryId: Long? = null
        set(value) {
            field = value
            interactor.doActionAsync(value)
        }

    @MainThread
    fun requestNewPage() {
        interactor.doActionAsync(countryId)
    }

}