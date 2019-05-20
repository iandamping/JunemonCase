package com.junemon.junemoncase.ui.fragment.orderbucketlist.wishlist

import com.junemon.junemoncase.base.BaseFragmentView
import com.junemon.junemoncase.model.OrderCasingModel

/**
 *
Created by Ian Damping on 16/05/2019.
Github = https://github.com/iandamping
 */
interface WishListView : BaseFragmentView {
    fun onSuccessGetListCaseData(data: List<OrderCasingModel>?)
    fun onFailedGetListCaseData(msg: String)
    fun onNotLoginYet()
}