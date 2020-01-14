//package com.androidjunior.coroutinestest.presentation.repos.details
//
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.Transformations.map
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.androidjunior.coroutinestest.usecases.base.State
//import com.androidjunior.coroutinestest.usecases.repos.GetRepoDetailsInteractorSync
//import com.androidjunior.coroutinestest.usecases.repos.RepoDetails
//import kotlinx.coroutines.launch
//import org.koin.core.KoinComponent
//import org.koin.core.inject
//import org.koin.core.parameter.parametersOf
//import androidx.lifecycle.Transformations.distinctUntilChanged as untilChanged
//
//class RepoDetailsViewModel : ViewModel(), KoinComponent {
//    private val interactor: GetRepoDetailsInteractorSync by inject { parametersOf(viewModelScope) }
//    private val data = MutableLiveData<RepoDetails>()
//
//    val isError = untilChanged(map(interactor.state) { it is State.Error })
//    val isLoading = untilChanged(map(interactor.state) { it is State.Loading })
//    val error =
//        untilChanged(map(interactor.state) { if (it is State.Error) it.message else "" })
//    val name = map(data) { it.name }
//    val description = map(data) { it.description }
//    val language = map(data) { it.language }
//
//    fun setRepoName(repoName: String) {
//        interactor.repoName = repoName
//        viewModelScope.launch {
//            data.postValue(interactor.get())
//        }
//    }
//}