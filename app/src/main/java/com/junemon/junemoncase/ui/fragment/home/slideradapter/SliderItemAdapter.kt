package com.junemon.junemoncase.ui.fragment.home.slideradapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.junemon.junemoncase.R
import com.junemon.junemoncase.model.AllCasingModel
import com.junemon.junemoncase.util.inflates
import com.junemon.junemoncase.util.loadUrl
import kotlinx.android.synthetic.main.item_slider.view.*

/**
 *
Created by Ian Damping on 16/04/2019.
Github = https://github.com/iandamping
 */
class SliderItemAdapter(private val data: List<AllCasingModel>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val views = container.inflates(R.layout.item_slider)
        views.ivSliderImage.loadUrl(data[position].photoUrl)
        container.addView(views)
        return views
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int = data.size
}