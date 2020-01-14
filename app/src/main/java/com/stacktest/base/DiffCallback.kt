package com.stacktest.base

import androidx.recyclerview.widget.DiffUtil

class DiffCallback : DiffUtil.Callback() {

    private var items = emptyList<Any>()
    private var oldItems = emptyList<Any>()

    val size: Int
        get() = items.size

    fun getItemAt(position: Int) = items[position]

    fun update(newItems: List<Any>) {
        oldItems = items
        items = newItems
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition] == items[newItemPosition]

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = items.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition] == items[newItemPosition]
}