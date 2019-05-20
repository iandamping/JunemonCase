package com.junemon.junemoncase.ui.fragment.profile

import android.content.Context
import android.view.View
import com.junemon.junemoncase.base.MyCustomBaseFragmentPresenter
import com.junemon.junemoncase.util.logE

/**
 *
Created by Ian Damping on 18/04/2019.
Github = https://github.com/iandamping
 */
class ProfilePresenter : MyCustomBaseFragmentPresenter<ProfileView>() {
    private var ctx: Context? = null

    override fun onAttach() {
        this.ctx = getLifeCycleOwner().context
        onGetUserData({
            view()?.onSuccessGetData(it)
        }) {
            logE("null")
        }
    }

    override fun onCreateView(view: View) {
        view()?.initView(view)
    }

    fun logOut() {
        setUserLogout()
    }
}