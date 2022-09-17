package com.rummenigged.popcorntime.view.common

import android.util.Log
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T>(@NonNull private val values: ArrayList<T> = ArrayList()): RecyclerView.Adapter<BaseAdapter.BaseViewHolder<T>>() {
    protected lateinit var itemClickListener: (View, Int) -> Unit
    protected lateinit var rootRecyclerView: RecyclerView

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        if (values.isNotEmpty()) {
            val item = values[position]
            holder.bind(item)
            holder.itemView.setOnClickListener {
                try {
                    itemClickListener.invoke(holder.itemView, position)
                }catch (e: UninitializedPropertyAccessException){
                    Log.e(BaseAdapter::class.java.toString(), "$itemClickListener was not initialized", e)
                }
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        rootRecyclerView = recyclerView
    }

    fun subscribeToItemSelection(listener: (view: View, position: Int) -> Unit){
        itemClickListener = listener
    }

    open fun swapData(list: List<T>){
        values.clear()
        values.addAll(list)
        notifyDataSetChanged()
    }

    @Throws(IndexOutOfBoundsException::class)
    fun getItem(position: Int): T = values[position]

    override fun getItemCount(): Int = if (values.isNullOrEmpty()) 0 else values.size

    abstract class BaseViewHolder<T>(itemBinding: ViewBinding): RecyclerView.ViewHolder(itemBinding.root){
        abstract fun bind(item: T)
    }
}