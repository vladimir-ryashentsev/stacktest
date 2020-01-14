package com.stacktest.regions

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androidjunior.coroutinestest.R
import com.androidjunior.coroutinestest.databinding.RegionsItemBinding
import com.stacktest.base.DiffCallback
import com.stacktest.domain.regions.Region


class RegionsAdapter(
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

    fun setRegions(regions: List<Region>) {
        Log.d("BLABLA", "RegionsAdapter setRegions: $regions")
        originalItems = regions
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
            ITEM_TYPE_REGION
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

        return RegionViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.regions_item,
                parent,
                false
            )
        )
    }


    override fun getItemCount() = diffCallback.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = diffCallback.getItemAt(position)
        if (item is Region) {
            (holder as RegionViewHolder).setRegion(item)
            if (item == originalItems.last())
                onLastItemShown.invoke()
        }
    }

    class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)

    inner class RegionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = DataBindingUtil.bind<RegionsItemBinding>(view)

        fun setRegion(region: Region) {
            binding!!.region = region
        }
    }

    companion object {
        private const val ITEM_TYPE_LOADING = 0
        private const val ITEM_TYPE_REGION = 1
        private val LOADING_ITEM = Any()
    }
}