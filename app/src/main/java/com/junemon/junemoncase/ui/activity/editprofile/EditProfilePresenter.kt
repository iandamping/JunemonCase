package com.junemon.junemoncase.ui.activity.editprofile

import com.google.firebase.database.DatabaseReference
import com.ian.app.helper.util.asyncRxExecutor
import com.ian.app.helper.util.singleExecutes
import com.ian.app.helper.util.singleZipWith
import com.junemon.junemoncase.JunemonApps.Companion.DatabasesAccess
import com.junemon.junemoncase.JunemonApps.Companion.api
import com.junemon.junemoncase.base.MyCustomBasePresenter
import com.junemon.junemoncase.model.UserProfileModel


/**
 *
Created by Ian Damping on 26/04/2019.
Github = https://github.com/iandamping
 */
class EditProfilePresenter(private val dataRef: DatabaseReference) : MyCustomBasePresenter<EditProfileView>() {
    private var tmpMutableData: MutableMap<String, String> = mutableMapOf()
    private var currentUserId: String? = null
    private var localCurrentUserID: Int? = null
    private var localCurrentPhotoUser: String? = null
    override fun onCreate() {
        view()?.initView()
        getAllProvinceAndCity()
        onGetUserData({
            this.currentUserId = it.userID
            this.localCurrentUserID = it.local_user_id
            this.localCurrentPhotoUser = it.photoUser
            view()?.onGetUserData(it)
        }) {
            view()?.onNotLoginYet()
        }
    }

    fun updateUserData(data: UserProfileModel) {
        setDialogShow(false)
        if (currentUserId != null) {
            data.local_user_id = localCurrentUserID
            data.photoUser = localCurrentPhotoUser
            currentUserId?.let { nonNullUserID -> tmpMutableData.put("userID", nonNullUserID) }
            data.nameUser?.let { name -> tmpMutableData.put("nameUser", name) }
            data.emailUser?.let { email -> tmpMutableData.put("emailUser", email) }
            data.addressUser?.let { address -> tmpMutableData.put("addressUser", address) }
            data.cityUser?.let { city -> tmpMutableData.put("cityUser", city) }
            data.phoneNumberUser?.let { number -> tmpMutableData.put("phoneNumberUser", number) }
            data.provinceUser?.let { province -> tmpMutableData.put("provinceUser", province) }
            dataRef.child(currentUserId!!).updateChildren(tmpMutableData as Map<String, String>).addOnCompleteListener {
                if (it.isSuccessful) {
                    compose.asyncRxExecutor {
                        DatabasesAccess?.userDao()?.updateLocalUserData(data)
                    }
//                    prefHelper.saveStringInSharedPreference(saveUserData, gson.toJson(data))
                    setDialogShow(true)
                    view()?.onSuccessEditProfile()
                }
            }.addOnFailureListener {
                setDialogShow(true)
                view()?.onFailEditProfile(it.localizedMessage)
            }
        }
    }


    private fun getAllProvinceAndCity() {
        setDialogShow(false)
        compose.singleExecutes(api.getAllCityData().singleZipWith(api.getAllProvinceData()), {
            setDialogShow(true)
            view()?.onFailGetRajaOngkirData(it?.localizedMessage)
        }, {
            setDialogShow(true)
            it?.first?.let { city -> view()?.onGetCityData(city.allData?.results) }
            it?.second?.let { province -> view()?.onGetProvinceData(province.allData?.results) }

        })
    }
}