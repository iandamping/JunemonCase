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
import com.junemon.junemoncase.util.zipWith


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
        getAllProvinceAndCity()
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
                    mView.onSuccessEditProfile()
                }
            }.addOnFailureListener {
                setDialogShow(true)
                mView.onFailEditProfile(it.localizedMessage)
            }
        }
    }

    private fun onGetUserData() {
        listener = FirebaseAuth.AuthStateListener {
            if (it != null) {
                if (!prefHelper.getStringInSharedPreference(saveUserData).isNullOrBlank()) {
                    this.currentUserId = it.currentUser?.uid
                    mView.onGetUserData(
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
                        this.currentUserId = it.currentUser?.uid
                        mView.onGetUserData(userData)
                    }
                }

            }
        }
    }

    fun onDestroy() {
        if (compose != null && compose.isDisposed) {
            compose.dispose()
        }
    }

    private fun getAllProvinceAndCity() {
        setDialogShow(false)
        compose.executes(api.getAllCityData().zipWith(api.getAllProvinceData()),{
            setDialogShow(true)
            mView.onFailGetRajaOngkirData(it.localizedMessage)
        },{
            setDialogShow(true)
            it?.first?.let { city ->  mView.onGetCityData(city.allData?.results)}
            it?.second?.let { province -> mView.onGetProvinceData(province.allData?.results)}

        })
    }
}