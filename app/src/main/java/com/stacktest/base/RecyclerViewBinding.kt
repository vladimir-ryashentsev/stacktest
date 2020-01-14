package com.stacktest.base

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.stacktest.countries.CountriesAdapter
import com.stacktest.domain.countries.Country
import com.stacktest.domain.regions.Region
import com.stacktest.regions.RegionsAdapter

@BindingAdapter("countriesLiveData")
fun <T> setRecyclerViewCountries(
    view: RecyclerView,
    countriesLiveData: LiveData<List<Country>>
) {
    val lifecycleOwner = (view.context as AppCompatActivity)
    if (view.adapter is CountriesAdapter) {
        countriesLiveData.observe(lifecycleOwner, Observer {
            (view.adapter as CountriesAdapter).setCountries(it)
        })
    }
}

@BindingAdapter("regionsLiveData")
fun <T> setRecyclerViewRegions(
    view: RecyclerView,
    regionsLiveData: LiveData<List<Region>>
) {
    Log.d("BLABLA", "set regions livedata")
    val lifecycleOwner = (view.context as AppCompatActivity)
    if (view.adapter is RegionsAdapter) {
        regionsLiveData.observe(lifecycleOwner, Observer {
            (view.adapter as RegionsAdapter).setRegions(it)
        })
    }
}

@BindingAdapter("loading")
fun <T> setRecyclerViewProperties(
    view: RecyclerView,
    show: Boolean
) {
    val adapter = view.adapter
    if (adapter is CountriesAdapter)
        adapter.setLoading(show)
    else if (adapter is RegionsAdapter)
        adapter.setLoading(show)
}