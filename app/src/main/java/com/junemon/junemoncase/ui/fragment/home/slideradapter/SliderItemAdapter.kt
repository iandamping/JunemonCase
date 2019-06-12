package com.junemon.junemoncase.ui.fragment.home.slideradapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.ian.app.helper.util.inflates
import com.ian.app.helper.util.loadWithGlide
import com.ian.app.helper.util.startActivity
import com.junemon.junemoncase.JunemonApps.Companion.gson
import com.junemon.junemoncase.R
import com.junemon.junemoncase.model.AllCasingModel
import com.junemon.junemoncase.ui.activity.detail.DetailActivity
import com.junemon.junemoncase.util.Constant
import kotlinx.android.synthetic.main.item_slider.view.*

/**
 *
Created by Ian Damping on 16/04/2019.
Github = https://github.com/iandamping
 */
class SliderItemAdapter(private val data: List<AllCasingModel>, private val ctx: Context?) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val views = container.inflates(R.layout.item_slider)
        views.ivSliderImage.loadWithGlide(data[position].photoUrl)
        views.ivSliderImage?.setOnClickListener {

            ctx?.startActivity<DetailActivity> {
                putExtra(Constant.seeDetailKey, gson.toJson(this@SliderItemAdapter.data[position]))
            }
        }
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