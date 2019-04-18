package com.junemon.junemoncase.ui.activity.detail

import android.content.Context
import com.junemon.junemoncase.JunemonApps.Companion.gson
import com.junemon.junemoncase.base.BasePresenter
import com.junemon.junemoncase.model.AllCasingModel

/**
 *
Created by Ian Damping on 18/04/2019.
Github = https://github.com/iandamping
 */
class DetailActivityPresenter(private val mView: DetailActivityView) : BasePresenter() {

    override fun onCreate(context: Context) {
        mView.initView()
    }

    fun onGetDataPassed(data: String?) {
        mView.onSuccesGetData(gson.fromJson(data, AllCasingModel::class.java))
    }
}