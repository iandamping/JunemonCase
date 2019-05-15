package com.junemon.junemoncase.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junemon.junemoncase.util.inflates
import kotlinx.android.extensions.LayoutContainer

abstract class MyAbstractAdapter<T>(protected var data:List<T>, private val layout:Int, private val clickListener:(T) ->Unit):
    RecyclerView.Adapter<MyAbstractAdapter.MyAbstractViewHolder>() {

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAbstractViewHolder {
        return MyAbstractViewHolder(parent.inflates(layout))
    }

    override fun onBindViewHolder(holder: MyAbstractViewHolder, position: Int) {
        val item = data[position]
        holder.itemView.bind(item)
        holder.itemView.setOnClickListener { clickListener(item) }
    }

    protected open fun View.bind(item: T) {
    }

    class MyAbstractViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer
}