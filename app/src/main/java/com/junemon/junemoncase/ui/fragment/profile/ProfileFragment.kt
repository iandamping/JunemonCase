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
        data?.let {
            actualView?.ivPhotoProfile?.loadUrl(it.photoUser)
            actualView?.tvProfileName?.text = it.nameUser
            actualView?.tvEmailUser?.text = it.emailUser
            actualView?.lnProfileAddress?.visible()
            actualView?.tvProfileAddress?.text = it.addressUser
            actualView?.lnProfileProvince?.visible()
            actualView?.tvProfileProvince?.text = it.provinceUser
            actualView?.lnProfileCity?.visible()
            actualView?.tvProfileCity?.text = it.cityUser
            actualView?.lnProfileEmail?.visible()
            actualView?.tvEmailUser?.text = it.emailUser
            actualView?.lnProfilePhoneNumber?.visible()
            actualView?.tvProfilePhoneNumber?.text = it.phoneNumberUser
            actualView?.btnProfileEditUser?.setOnClickListener {
                context?.startActivity<EditProfileActivity>()
            }
//            when {
//                it.photoUser != null ->
//                it.nameUser != null ->
//                it.emailUser != null ->
//                it.addressUser != null -> {
//
//                }
//                it.provinceUser != null -> {
//
//                }
//                it.cityUser != null -> {
//
//                }
//                it.emailUser != null -> {
//
//                }
//                it.phoneNumberUser != null -> {
//
//                }
//                else -> logE("dunno")
//            }
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