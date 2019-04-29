package com.junemon.junemoncase.util

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.junemon.junemoncase.base.KotlinAdapter

/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */
fun <T> RecyclerView.setUpHorizontal(
        items: List<T>,
        layoutResId: Int,
        bindHolder: View.(T) -> Unit,
        itemClick: T.() -> Unit = {},
        manager: RecyclerView.LayoutManager = LinearLayoutManager(
                this.context,
                LinearLayoutManager.HORIZONTAL,
                false
        )
): KotlinAdapter<T> {
    val snapHelper = RecyclerHorizontalSnapHelper()
    if (this.onFlingListener == null) {
        snapHelper.attachToRecyclerView(this)
    }
    return KotlinAdapter(items, layoutResId, { bindHolder(it) }, {
        itemClick()
    }).apply {
        layoutManager = manager
        adapter = this
    }
}

fun <T> RecyclerView.setUpWithGrid(
        items: List<T>, layoutResId: Int, gridSize: Int, bindHolder: View.(T) -> Unit, itemClick: T.() -> Unit = {},
        manager: RecyclerView.LayoutManager = GridLayoutManager(this.context, gridSize)
): KotlinAdapter<T> {

    return KotlinAdapter(items, layoutResId, { bindHolder(it) }, {
        itemClick()
    }).apply {
        layoutManager = manager
        adapter = this
    }
}