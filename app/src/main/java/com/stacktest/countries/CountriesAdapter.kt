package com.stacktest.countries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androidjunior.coroutinestest.R
import com.androidjunior.coroutinestest.databinding.CountriesItemBinding
import com.stacktest.base.DiffCallback
import com.stacktest.domain.countries.Country


class CountriesAdapter(
    private val itemClickListener: (country: Country) -> Unit,
    private val onLastItemShown: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = DiffCallback()
    private var isLoading = false

    private var originalItems = emptyList<Any>()
    private var items = emptyList<Any>()

    fun setLoading(loading: Boolean) {
        if (isLoading != loading) {
            isLoading = loading
            rebuildItems()
        }
    }

    fun setCountries(countries: List<Country>) {
        originalItems = countries
        rebuildItems()
    }

    private fun rebuildItems() {
        items = originalItems
        if (isLoading)
            items = items + LOADING_ITEM

        diffCallback.update(items)
        val result = DiffUtil.calculateDiff(diffCallback)
        result.dispatchUpdatesTo(this)
    }


    override fun getItemViewType(position: Int): Int {
        return if (diffCallback.getItemAt(position) == LOADING_ITEM)
            ITEM_TYPE_LOADING
        else
            ITEM_TYPE_COUNTRY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_TYPE_LOADING)
            return LoadingViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.loading_item,
                    parent,
                    false
                )
            )

        return CountryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.countries_item,
                parent,
                false
            )
        )
    }


    override fun getItemCount() = diffCallback.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = diffCallback.getItemAt(position)
        if (item is Country) {
            (holder as CountryViewHolder).setCountry(item)
            if (item == originalItems.last())
                onLastItemShown.invoke()
        }
    }

    class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)

    inner class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = DataBindingUtil.bind<CountriesItemBinding>(view)

        init {
            view.setOnClickListener { itemClickListener.invoke(binding!!.country!!) }
        }

        fun setCountry(country: Country) {
            binding!!.country = country
        }
    }

    companion object {
        private const val ITEM_TYPE_LOADING = 0
        private const val ITEM_TYPE_COUNTRY = 1
        private val LOADING_ITEM = Any()
    }
}