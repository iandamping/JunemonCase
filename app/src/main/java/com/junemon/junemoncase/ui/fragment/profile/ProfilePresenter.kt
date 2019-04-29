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
import com.junemon.junemoncase.model.UserProfileModel
import com.junemon.junemoncase.ui.activity.MainActivity
import com.junemon.junemoncase.util.Constant.saveUserData
import com.junemon.junemoncase.util.startActivity

/**
 *
Created by Ian Damping on 18/04/2019.
Github = https://github.com/iandamping
 */
class ProfilePresenter(private val mView: ProfileView, private val userDataReference: DatabaseReference) :
        BaseFragmentPresenter() {
    private var ctx: Context? = null
    private var currentUser: FirebaseUser? = null
    private lateinit var listener: FirebaseAuth.AuthStateListener
    private lateinit var userData: UserProfileModel

    override fun onAttach(context: Context?) {
        this.ctx = context
        currentUser = mFirebaseAuth.currentUser
    }

    override fun onCreateView(view: View) {
        mView.initView(view)
    }

    fun onResume() {
        if (listener != null) {
            mFirebaseAuth.addAuthStateListener(listener)
        }
    }

    fun onPause() {
        if (listener != null) {
            mFirebaseAuth.removeAuthStateListener(listener)
        }
    }

    fun onGetUserData() {
        listener = FirebaseAuth.AuthStateListener {
            if (it != null) {
                if (!prefHelper.getStringInSharedPreference(saveUserData).isNullOrBlank()) {
                    mView.onSuccessGetData(
                            gson.fromJson(
                                    prefHelper.getStringInSharedPreference(saveUserData),
                                    UserProfileModel::class.java
                            )
                    )
                } else if (prefHelper.getStringInSharedPreference(saveUserData).isNullOrBlank()) {
                    if (it.currentUser != null) {
                        userData = UserProfileModel(
                                it.currentUser?.photoUrl.toString(),
                                it.currentUser?.displayName,
                                it.currentUser?.email,
                                it.currentUser?.phoneNumber,
                                null,
                                null,
                                null
                        )
                        it.currentUser?.uid?.let { currentUserData ->
                            userDataReference.child(currentUserData).setValue(userData)
                        }
                        prefHelper.saveStringInSharedPreference(saveUserData, gson.toJson(userData))
                        ctx?.startActivity<MainActivity>()
                    }
                }
            }
        }
    }

    fun setUserLogout() {
        ctx?.let { AuthUI.getInstance().signOut(it) }
        prefHelper.deleteSharedPreference()
        ctx?.startActivity<MainActivity>()
    }
}