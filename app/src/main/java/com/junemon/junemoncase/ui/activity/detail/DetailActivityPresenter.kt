package com.junemon.junemoncase.ui.activity.detail

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.google.firebase.database.DatabaseReference
import com.junemon.junemoncase.JunemonApps.Companion.gson
import com.junemon.junemoncase.base.BasePresenter
import com.junemon.junemoncase.data.GenericViewModel
import com.junemon.junemoncase.model.AllCasingModel
import com.junemon.junemoncase.model.PhoneTypeModel
import com.junemon.junemoncase.util.getAllDataFromFirebase
import com.junemon.junemoncase.util.withViewModel


/**
 *
Created by Ian Damping on 18/04/2019.
Github = https://github.com/iandamping
 */
class DetailActivityPresenter(
        private val dataRef: DatabaseReference,
        private val mView: DetailActivityView,
        private val target: FragmentActivity
) : BasePresenter() {
    override fun onCreate(context: Context) {
        setBaseDialog(context)
        mView.initView()
    }

    fun onGetDataPassed(data: String?) {
        mView.onSuccesGetData(gson.fromJson(data, AllCasingModel::class.java))
    }


    fun onGetPhoneTypeData(phoneName: String?) {
        setDialogShow(false)
        target.getAllDataFromFirebase<PhoneTypeModel>(dataRef)
        target.withViewModel<GenericViewModel<PhoneTypeModel>> {
            this.getGenericData().observe(target, Observer {
                val message = it.listPhoneTypedata?.get(phoneName).toString()
                if (message != "null") {
                    setDialogShow(true)
                    mView.onSuccessPhoneAvailable(true)
                } else {

                    setDialogShow(true)
                }
            })
        }
    }


}