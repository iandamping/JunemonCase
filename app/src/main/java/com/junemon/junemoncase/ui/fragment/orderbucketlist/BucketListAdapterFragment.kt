package com.junemon.junemoncase.ui.fragment.orderbucketlist

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.junemon.junemoncase.ui.fragment.orderbucketlist.donepaymentlist.DonePaymentListFragment
import com.junemon.junemoncase.ui.fragment.orderbucketlist.wishlist.WishListFragment

class BucketListAdapterFragment(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    private val pageCount: Int = 2
    private val tabTitle = arrayOf("Wish List", "Done Payment")


    override fun getItem(position: Int): Fragment {
        var fragment: Fragment = WishListFragment()
        when (position) {
            1 -> fragment = DonePaymentListFragment()
        }
        return fragment
    }


    override fun getCount(): Int {
        return pageCount
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitle[position]
    }
}