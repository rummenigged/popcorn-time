package com.rummenigged.popcorntime.view.common

import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil

abstract class DiffUtilBaseAdapter<T: DiffUtilItemList<T>>(@NonNull private val values: ArrayList<T>): BaseAdapter<T>(values) {

    override fun swapData(list: List<T>) {
        DiffUtil.calculateDiff(AdapterDiffCallback(list, values)).dispatchUpdatesTo(this)
        values.clear()
        values.addAll(list)
    }

    private inner class AdapterDiffCallback(private val newList: List<T>,
                                            private val oldList: List<T>): DiffUtil.Callback(){

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            newList[newItemPosition].isItemEqualTo(oldList[oldItemPosition])

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int  = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            newList[newItemPosition].isContentEqualTo(oldList[oldItemPosition])
    }
}