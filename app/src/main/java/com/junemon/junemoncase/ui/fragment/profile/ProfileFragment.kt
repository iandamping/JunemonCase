package com.junemon.junemoncase.ui.fragment.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.AuthUI
import com.junemon.junemoncase.R
import com.junemon.junemoncase.model.UserProfileModel
import com.junemon.junemoncase.ui.activity.editprofile.EditProfileActivity
import com.junemon.junemoncase.util.*
import com.junemon.junemoncase.util.Constant.RequestSignIn
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
        presenter = ProfilePresenter().apply {
            attachView(this@ProfileFragment, this@ProfileFragment)
            onAttach()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views = container?.inflates(R.layout.fragment_profile)
        views?.let { presenter.onCreateView(it) }
        return views
    }

    override fun onSuccessGetData(data: UserProfileModel?) {
        if (data?.photoUser != null) {
            actualView?.ivPhotoProfile?.loadUrl(data.photoUser)
        }
        if (data?.nameUser != null) {
            actualView?.tvProfileName?.text = data.nameUser
        }
        if (data?.emailUser != null) {
            actualView?.tvEmailUser?.text = data.emailUser
        }
        if (data?.addressUser != null) {
            actualView?.lnProfileAddress?.visible()
            actualView?.tvProfileAddress?.text = data.addressUser
        }
        if (data?.provinceUser != null) {
            actualView?.lnProfileProvince?.visible()
            actualView?.tvProfileProvince?.text = data.provinceUser
        }
        if (data?.cityUser != null) {
            actualView?.lnProfileCity?.visible()
            actualView?.tvProfileCity?.text = data.cityUser
        }
        if (data?.emailUser != null) {
            actualView?.lnProfileEmail?.visible()
            actualView?.tvEmailUser?.text = data.emailUser
        }
        if (data?.phoneNumberUser != null) {
            actualView?.lnProfilePhoneNumber?.visible()
            actualView?.tvProfilePhoneNumber?.text = data.phoneNumberUser
        }
        actualView?.btnProfileEditUser?.setOnClickListener {
            context?.startActivity<EditProfileActivity>()
        }



        actualView?.btnLogin?.text = context?.getString(R.string.logout)
        actualView?.btnLogin?.setOnClickListener {
            presenter.logOut()
        }
    }

    override fun onFailedGetData() {
        actualView?.tvProfileName?.gone()
        actualView?.btnProfileEditUser?.gone()
        actualView?.lnProfileAddress?.gone()
        actualView?.lnProfileProvince?.gone()
        actualView?.lnProfileCity?.gone()
        actualView?.lnProfileEmail?.gone()
        actualView?.lnProfilePhoneNumber?.gone()
    }

    override fun initView(view: View) {
        this.actualView = view
        actualView?.btnLogin?.setOnClickListener {
            createSignInIntent()
        }
        actualView?.ivSettingProfile?.setOnClickListener {

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

}