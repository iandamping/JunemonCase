package com.junemon.junemoncase.ui.fragment.orderbucketlist

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import com.ian.app.helper.util.inflates
import com.junemon.junemoncase.R
import kotlinx.android.synthetic.main.fragment_bucketlist.view.*

/**
 *
Created by Ian Damping on 15/05/2019.
Github = https://github.com/iandamping
 */
class BucketListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views = container?.inflates(R.layout.fragment_bucketlist)
        with(views) {
            this?.let { nonNullView ->
                nonNullView.tabMyItemPage.setSelectedTabIndicatorColor(Color.parseColor("#1554A1"))
                nonNullView.tabMyItemPage.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#235EA7"))
                nonNullView.tabMyItemPage.setupWithViewPager(nonNullView.vpMyItemPage)
                nonNullView.vpMyItemPage.adapter = buildAdapter()

            }
        }
        return views
    }

    private fun buildAdapter(): PagerAdapter {
        return BucketListAdapterFragment(childFragmentManager)
    }
}