package com.junemon.junemoncase.ui.activity.ordercasing

import androidx.lifecycle.Observer
import com.google.firebase.database.DatabaseReference
import com.junemon.junemoncase.JunemonApps.Companion.gson
import com.junemon.junemoncase.R
import com.junemon.junemoncase.base.MyCustomBasePresenter
import com.junemon.junemoncase.data.GenericViewModel
import com.junemon.junemoncase.model.AllCasingModel
import com.junemon.junemoncase.model.OrderCasingModel
import com.junemon.junemoncase.model.PhoneTypeModel
import com.junemon.junemoncase.model.UserProfileModel
import com.junemon.junemoncase.util.getAllDataFromFirebase
import com.junemon.junemoncase.util.withViewModel

/**
 *
Created by Ian Damping on 24/04/2019.
Github = https://github.com/iandamping
 */
class OrderCasePresenter(private val dataRef: DatabaseReference) : MyCustomBasePresenter<OrderCaseView>() {

    override fun onCreate() {
        view()?.initView()
        listAllPhones = getLifeCycleOwner().resources.getStringArray(R.array.all_phone_names)
        getLifeCycleOwner().getAllDataFromFirebase<PhoneTypeModel>(dataRef)

        onGetUserData({
            when {
                it.cityUser.isNullOrBlank() || it.provinceUser.isNullOrBlank() || it.addressUser.isNullOrBlank() -> view()?.onEditUserFirst()
                !it.cityUser.isNullOrBlank() || !it.provinceUser.isNullOrBlank() || !it.addressUser.isNullOrBlank() -> view()?.onGetUserData(it)
            }
        }, {
            view()?.onLoginFirst()
        })
    }

    private var tmpListData: MutableList<String> = mutableListOf()
    private var listAllPhones: Array<String>? = null


    fun setInitialData(data: String?) {
        view()?.onGetDetailedData(gson.fromJson(data, AllCasingModel::class.java))
    }

    fun onGetPhoneTypeData(phoneName: String?) {
        setDialogShow(false)
        getLifeCycleOwner().withViewModel<GenericViewModel<PhoneTypeModel>> {
            this.getGenericData().observe(getLifeCycleOwner(), Observer {
                val data = it.listPhoneTypedata?.get(phoneName).toString()
                if (data != "null") {
                    val tempdata = data.replace("PhoneType(listCasingType=[", "").replace("])", "")
                    tmpListData.clear()
                    tmpListData = tempdata.split(",").toMutableList()
                    setDialogShow(true)
                    view()?.onSuccessGetListPhoneType(tmpListData)
                } else {
                    setDialogShow(true)
                    view()?.onFailedGetListPhoneType("Failed get data")
                }
            })
        }
    }

    fun onUploadOrderCase(dataRef: DatabaseReference, casingData: AllCasingModel?, profileData: UserProfileModel?, phonePassedType: String?) {
        val orderedCase = OrderCasingModel()
        setDialogShow(false)
        with(orderedCase) {
            nameUser = profileData?.nameUser
            addressUser = profileData?.addressUser
            phoneNumberUser = profileData?.phoneNumberUser
            provinceUser = profileData?.provinceUser
            cityUser = profileData?.cityUser
            casingType = casingData?.casingType
            photoUrl = casingData?.photoUrl
            userID = profileData?.userID
            phoneType = phonePassedType
        }
        dataRef.push().setValue(orderedCase).addOnCompleteListener {
            if (it.isSuccessful) {
                setDialogShow(true)
                view()?.onSuccessOrderCase()
            }
            if (it.isCanceled) {
                view()?.onFailOrderCase()
            }
        }
    }
}