package com.junemon.junemoncase.ui.fragment.profile

import android.content.Context
import android.view.View
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.junemon.junemoncase.JunemonApps.Companion.gson
import com.junemon.junemoncase.JunemonApps.Companion.mFirebaseAuth
import com.junemon.junemoncase.JunemonApps.Companion.prefHelper
import com.junemon.junemoncase.base.BaseFragmentPresenter
import com.junemon.junemoncase.base.MyCustomBaseFragmentPresenter
import com.junemon.junemoncase.model.UserProfileModel
import com.junemon.junemoncase.ui.activity.MainActivity
import com.junemon.junemoncase.util.Constant
import com.junemon.junemoncase.util.Constant.saveUserData
import com.junemon.junemoncase.util.logE
import com.junemon.junemoncase.util.startActivity

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