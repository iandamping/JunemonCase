package com.junemon.junemoncase.ui.activity.seeall

import android.content.Context
import com.ian.app.helper.util.fromJsonHelper
import com.junemon.junemoncase.JunemonApps.Companion.gson
import com.junemon.junemoncase.base.BasePresenter
import com.junemon.junemoncase.model.AllCasingModel

/**
 *
Created by Ian Damping on 18/04/2019.
Github = https://github.com/iandamping
 */
class SeeAllPresenter(private val mView: SeeAllView) : BasePresenter() {

    override fun onCreate(context: Context) {
        mView.initView()
    }

    fun onGetPassedData(dataPassed: String?) {
        mView.onSuccesGetData(dataPassed?.let { gson.fromJsonHelper<List<AllCasingModel>>(it) })
    }
}