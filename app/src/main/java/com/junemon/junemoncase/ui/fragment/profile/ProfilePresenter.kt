package com.junemon.junemoncase.ui.fragment.profile

import android.content.Context
import android.view.View
import com.google.firebase.auth.FirebaseUser
import com.junemon.junemoncase.JunemonApps.Companion.mFirebaseAuth
import com.junemon.junemoncase.base.MyCustomBaseFragmentPresenter
import com.junemon.junemoncase.util.logE

/**
 *
Created by Ian Damping on 18/04/2019.
Github = https://github.com/iandamping
 */
class ProfilePresenter : MyCustomBaseFragmentPresenter<ProfileView>() {
    private var ctx: Context? = null
    private var currentUser: FirebaseUser? = null

    override fun onAttach() {
        this.ctx = getLifeCycleOwner().context
        currentUser = mFirebaseAuth.currentUser
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