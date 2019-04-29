package com.junemon.junemoncase.ui.activity.editprofile

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.junemon.junemoncase.JunemonApps
import com.junemon.junemoncase.JunemonApps.Companion.api
import com.junemon.junemoncase.JunemonApps.Companion.gson
import com.junemon.junemoncase.JunemonApps.Companion.mFirebaseAuth
import com.junemon.junemoncase.JunemonApps.Companion.prefHelper
import com.junemon.junemoncase.base.BasePresenter
import com.junemon.junemoncase.model.UserProfileModel
import com.junemon.junemoncase.util.Constant.saveUserData
import com.junemon.junemoncase.util.executes


/**
 *
Created by Ian Damping on 26/04/2019.
Github = https://github.com/iandamping
 */
class EditProfilePresenter(private val dataRef: DatabaseReference, private val mView: EditProfileView) :
        BasePresenter() {
    private var tmpMutableData: MutableMap<String, String> = mutableMapOf()
    private var currentUser: FirebaseUser? = null
    private lateinit var listener: FirebaseAuth.AuthStateListener
    private var userData: UserProfileModel? = null
    private var currentUserId: String? = null
    override fun onCreate(context: Context) {
        mView.initView()
        setBaseDialog(context)
        currentUser = mFirebaseAuth.currentUser
        getAllCity()
        getAllProvince()
        onGetUserData()
    }

    fun onResume() {
        if (listener != null) {
            JunemonApps.mFirebaseAuth.addAuthStateListener(listener)
        }
    }

    fun onPause() {
        if (listener != null) {
            JunemonApps.mFirebaseAuth.removeAuthStateListener(listener)
        }
    }

    fun updateUserData(data: UserProfileModel) {
        setDialogShow(false)
        if (currentUserId != null) {
            data.nameUser?.let { name -> tmpMutableData.put("nameUser", name) }
            data.emailUser?.let { email -> tmpMutableData.put("emailUser", email) }
            data.addressUser?.let { address -> tmpMutableData.put("addressUser", address) }
            data.cityUser?.let { city -> tmpMutableData.put("cityUser", city) }
            data.provinceUser?.let { province -> tmpMutableData.put("provinceUser", province) }
            dataRef.child(currentUserId!!).updateChildren(tmpMutableData as Map<String, String>).addOnCompleteListener {
                if (it.isSuccessful) {
                    prefHelper.saveStringInSharedPreference(saveUserData, gson.toJson(data))
                    setDialogShow(true)
                }
            }
        }
    }

    private fun onGetUserData() {
        listener = FirebaseAuth.AuthStateListener {
            if (it != null) {
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
                    this.currentUserId = it.currentUser?.uid
                    mView.onGetUserData(userData)
                }
            }
        }
    }


    private fun getAllCity() {
        setDialogShow(false)
        api.getAllCityData().executes({
            setDialogShow(true)
            mView.onFailGetRajaOngkirData(it.localizedMessage)
        }, { response ->
            setDialogShow(true)
            if (response != null) {
                mView.onGetCityData(response.allData?.results)
            }
        })
    }

    private fun getAllProvince() {
        setDialogShow(false)
        api.getAllProvinceData().executes({
            setDialogShow(true)
            mView.onFailGetRajaOngkirData(it.localizedMessage)
        }, { response ->
            setDialogShow(true)
            if (response != null) {
                mView.onGetProvinceData(response.allData?.results)
            }
        })
    }
}