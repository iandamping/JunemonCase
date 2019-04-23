package com.junemon.junemoncase.ui.activity.detail

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.google.firebase.database.DatabaseReference
import com.junemon.junemoncase.JunemonApps.Companion.gson
import com.junemon.junemoncase.base.BasePresenter
import com.junemon.junemoncase.data.GenericViewModel
import com.junemon.junemoncase.model.AllCasingModel
import com.junemon.junemoncase.model.PhoneTypeData
import com.junemon.junemoncase.util.getAllDataFromFirebase
import com.junemon.junemoncase.util.logD
import com.junemon.junemoncase.util.withViewModel

/**
 *
Created by Ian Damping on 18/04/2019.
Github = https://github.com/iandamping
 */
class DetailActivityPresenter(private val dataRef: DatabaseReference, private val mView: DetailActivityView, private val target: FragmentActivity) : BasePresenter() {
    override fun onCreate(context: Context) {
        mView.initView()
        target.getAllDataFromFirebase<PhoneTypeData>(dataRef)
        onGetPhoneTypeData()
    }

    fun onGetDataPassed(data: String?) {
        mView.onSuccesGetData(gson.fromJson(data, AllCasingModel::class.java))
    }

    private fun onGetPhoneTypeData() {
        target.withViewModel<GenericViewModel<PhoneTypeData>> {
            this.getGenericData().observe(target, Observer {
                logD(it.listPhoneTypedata?.get("iPhone 3").toString() + " ini iphone 3")
                logD(it.listPhoneTypedata.toString() + " ini listphone")
            })
        }

    }
}