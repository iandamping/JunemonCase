package com.junemon.junemoncase.ui.activity.ordercasing

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.google.firebase.database.DatabaseReference
import com.junemon.junemoncase.JunemonApps.Companion.gson
import com.junemon.junemoncase.R
import com.junemon.junemoncase.base.BasePresenter
import com.junemon.junemoncase.data.GenericViewModel
import com.junemon.junemoncase.model.AllCasingModel
import com.junemon.junemoncase.model.PhoneTypeData
import com.junemon.junemoncase.util.getAllDataFromFirebase
import com.junemon.junemoncase.util.withViewModel

/**
 *
Created by Ian Damping on 24/04/2019.
Github = https://github.com/iandamping
 */
class OrderCasePresenter(private val dataRef: DatabaseReference, private val mView: OrderCaseView, private val target: FragmentActivity) : BasePresenter() {
    private var tmpListData: MutableList<String> = mutableListOf()
    private var listAllPhones: Array<String>? = null

    override fun onCreate(context: Context) {
        setBaseDialog(context)
        mView.initView()
        listAllPhones = context.resources.getStringArray(R.array.all_phone_names)
        target.getAllDataFromFirebase<PhoneTypeData>(dataRef)

    }

    fun setInitialData(data: String?) {
        mView.onGetDetailedData(gson.fromJson(data, AllCasingModel::class.java))
    }

    fun onGetPhoneTypeData(phoneName: String?) {
        setDialogShow(false)
        target.withViewModel<GenericViewModel<PhoneTypeData>> {
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
                    mView.onFailedGetListPhoneType("a")
                }
            })
        }
    }
}