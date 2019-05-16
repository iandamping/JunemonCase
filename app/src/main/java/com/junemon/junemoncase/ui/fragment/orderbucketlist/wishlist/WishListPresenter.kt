package com.junemon.junemoncase.ui.fragment.orderbucketlist.wishlist

import android.content.Context
import android.view.View
import com.junemon.junemoncase.base.MyCustomBaseFragmentPresenter
import com.junemon.junemoncase.ui.activity.MainActivity
import com.junemon.junemoncase.util.Constant
import com.junemon.junemoncase.util.logE
import com.junemon.junemoncase.util.startActivity

/**
 *
Created by Ian Damping on 16/05/2019.
Github = https://github.com/iandamping
 */
class WishListPresenter : MyCustomBaseFragmentPresenter<WishListView>() {
    private var ctx: Context? = null

    override fun onAttach() {
        this.ctx = getLifeCycleOwner().context
        onGetUserData({
            logE(it.nameUser + " name user")
            logE(it.userID + " id user")
        }) {
            ctx?.startActivity<MainActivity> {
                putExtra(Constant.switchBackToMain, "4")
            }
        }

    }

    override fun onCreateView(view: View) {
        view()?.initView(view)
    }

}