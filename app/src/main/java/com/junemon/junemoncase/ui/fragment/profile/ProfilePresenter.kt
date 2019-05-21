package com.junemon.junemoncase.ui.fragment.profile

import android.content.Context
import android.view.View
import com.junemon.junemoncase.base.MyCustomBaseFragmentPresenter

/**
 *
Created by Ian Damping on 18/04/2019.
Github = https://github.com/iandamping
 */
class ProfilePresenter : MyCustomBaseFragmentPresenter<ProfileView>() {
    private var ctx: Context? = null
    override fun onAttach() {
        this.ctx = getLifeCycleOwner().context

    }

    override fun onCreateView(view: View) {
        onGetUserData({
            view()?.onSuccessGetData(it)
        }) {
            view()?.onFailedGetData()
        }
        view()?.initView(view)
    }

    fun logOut() {
        setUserLogout()
    }
}