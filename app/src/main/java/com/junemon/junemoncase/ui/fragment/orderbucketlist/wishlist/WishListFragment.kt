package com.junemon.junemoncase.ui.fragment.orderbucketlist.wishlist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.junemon.junemoncase.model.OrderCasingModel

/**
 *
Created by Ian Damping on 15/05/2019.
Github = https://github.com/iandamping
 */
class WishListFragment: Fragment(),WishListView {
    lateinit var presenter: WishListPresenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = WishListPresenter().apply {
            attachView(this@WishListFragment,this@WishListFragment)
            onAttach()
        }
    }

    override fun onSuccessGetListCaseData(data: OrderCasingModel) {
    }

    override fun onFailedGetListCaseData(msg: String) {
    }

    override fun initView(view: View) {
    }
}