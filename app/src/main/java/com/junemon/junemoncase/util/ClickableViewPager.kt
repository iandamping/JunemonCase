package com.junemon.junemoncase.util

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager

class ClickableViewPager : ViewPager {

    private var mOnItemClickListener: OnItemClickListener? = null

    constructor(context: Context) : super(context) {
        setup()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setup()
    }

    private fun setup() {
        val tapGestureDetector = GestureDetector(context, TapGestureListener())

        setOnTouchListener { v, event ->
            tapGestureDetector.onTouchEvent(event)
            false
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private inner class TapGestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            if (mOnItemClickListener != null) {
                mOnItemClickListener!!.onItemClick(currentItem)
            }
            return true
        }
    }
}