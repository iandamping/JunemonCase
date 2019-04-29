package com.junemon.junemoncase.ui.fragment.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.AuthUI
import com.junemon.junemoncase.JunemonApps.Companion.userDatabaseReference
import com.junemon.junemoncase.R
import com.junemon.junemoncase.model.UserProfileModel
import com.junemon.junemoncase.ui.activity.editprofile.EditProfileActivity
import com.junemon.junemoncase.util.Constant.RequestSignIn
import com.junemon.junemoncase.util.inflates
import com.junemon.junemoncase.util.loadUrl
import com.junemon.junemoncase.util.startActivity
import kotlinx.android.synthetic.main.fragment_profile.view.*

/**
 *
Created by Ian Damping on 18/04/2019.
Github = https://github.com/iandamping
 */
class ProfileFragment : Fragment(), ProfileView {
    private lateinit var presenter: ProfilePresenter
    private var actualView: View? = null
    private val loginProvider = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = ProfilePresenter(this, userDatabaseReference)
        presenter.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.onGetUserData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views = container?.inflates(R.layout.fragment_profile)
        views?.let { presenter.onCreateView(it) }
        return views
    }

    override fun onSuccessGetData(data: UserProfileModel?) {
        actualView?.ivPhotoProfile?.loadUrl(data?.photoUser)
        actualView?.tvProfileName?.text = data?.nameUser
        actualView?.tvEmailUser?.text = data?.emailUser
        actualView?.tvProfilePhoneNumber?.text = data?.phoneNumberUser
        actualView?.btnLogin?.text = "Logut"
        actualView?.btnLogin?.setOnClickListener {
            presenter.setUserLogout()
        }
    }

    override fun onFailedGetData() {

    }

    override fun initView(view: View) {
        this.actualView = view
        actualView?.btnLogin?.setOnClickListener {
            createSignInIntent()
        }
        actualView?.ivSettingProfile?.setOnClickListener {
            //            presenter.setUserLogout()
            context?.startActivity<EditProfileActivity>()
        }
    }


    private fun createSignInIntent() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(loginProvider)
                        .build(),
                RequestSignIn
        )
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }
}