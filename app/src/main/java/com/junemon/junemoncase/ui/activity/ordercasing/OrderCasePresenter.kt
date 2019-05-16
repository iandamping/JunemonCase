package com.junemon.junemoncase.ui.activity.ordercasing

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.google.firebase.database.DatabaseReference
import com.junemon.junemoncase.JunemonApps.Companion.gson
import com.junemon.junemoncase.JunemonApps.Companion.prefHelper
import com.junemon.junemoncase.R
import com.junemon.junemoncase.base.BasePresenter
import com.junemon.junemoncase.data.GenericViewModel
import com.junemon.junemoncase.model.AllCasingModel
import com.junemon.junemoncase.model.OrderCasingModel
import com.junemon.junemoncase.model.PhoneTypeModel
import com.junemon.junemoncase.model.UserProfileModel
import com.junemon.junemoncase.util.Constant
import com.junemon.junemoncase.util.getAllDataFromFirebase
import com.junemon.junemoncase.util.withViewModel

/**
 *
Created by Ian Damping on 24/04/2019.
Github = https://github.com/iandamping
 */
class OrderCasePresenter(
        private val dataRef: DatabaseReference,
        private val mView: OrderCaseView,
        private val target: FragmentActivity
) : BasePresenter() {
    private var tmpListData: MutableList<String> = mutableListOf()
    private var listAllPhones: Array<String>? = null

    override fun onCreate(context: Context) {
        setBaseDialog(context)
        mView.initView()
        listAllPhones = context.resources.getStringArray(R.array.all_phone_names)
        onGetUserData()
        target.getAllDataFromFirebase<PhoneTypeModel>(dataRef)

    }

    fun setInitialData(data: String?) {
        mView.onGetDetailedData(gson.fromJson(data, AllCasingModel::class.java))
    }

    fun onGetPhoneTypeData(phoneName: String?) {
        setDialogShow(false)
        target.withViewModel<GenericViewModel<PhoneTypeModel>> {
            this.getGenericData().observe(target, Observer {
                val data = it.listPhoneTypedata?.get(phoneName).toString()
                if (data != "null") {
                    val tempdata = data.replace("PhoneType(listCasingType=[", "").replace("])", "")
                    tmpListData.clear()
                    tmpListData = tempdata.split(",").toMutableList()
                    setDialogShow(true)
                    mView.onSuccessGetListPhoneType(tmpListData)
                } else {
                    setDialogShow(true)
                    mView.onFailedGetListPhoneType("Failed get data")
                }
            })
        }
    }

    private fun onGetUserData() {
        val tmpUserData =
                gson.fromJson(prefHelper.getStringInSharedPreference(Constant.saveUserData), UserProfileModel::class.java)
        when {
            tmpUserData == null -> mView.onLoginFirst()
            tmpUserData.cityUser.isNullOrBlank() || tmpUserData.provinceUser.isNullOrBlank() || tmpUserData.addressUser.isNullOrBlank() -> mView.onEditUserFirst()
            tmpUserData.cityUser != null || tmpUserData.provinceUser != null || tmpUserData.addressUser != null -> mView.onGetUserData(
                    tmpUserData
            )
        }
    }

    fun onUploadOrderCase(dataRef: DatabaseReference, casingData: AllCasingModel?, profileData: UserProfileModel?) {
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
        }
        dataRef.push().setValue(orderedCase).addOnCompleteListener {
            if (it.isSuccessful) {
                setDialogShow(true)
                mView.onSuccessOrderCase()
            }
            if (it.isCanceled) {
                mView.onFailOrderCase()
            }
        }
    }
}