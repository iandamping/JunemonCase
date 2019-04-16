package com.junemon.junemoncase.base

import android.view.View

/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */
class KotlinAdapter<T>(data: List<T>, layout: Int, private val bindHolder: View.(T) -> Unit) :
    AbstractAdapter<T>(data, layout) {
    private var itemClick: T.() -> Unit = {}

    constructor(data: List<T>, layout: Int, bindHolder: View.(T) -> Unit, itemClick: T.() -> Unit = {}) : this(
        data,
        layout,
        bindHolder
    ) {
        this.itemClick = itemClick
    }


    override fun onBindViewHolder(holder: AbstractHolder, position: Int) {
        holder.itemView.bindHolder(data[position])
    }

    override fun onItemClick(itemView: View, position: Int) {
        data[position].itemClick()
    }
}