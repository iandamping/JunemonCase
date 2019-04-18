package com.junemon.junemoncase.ui.activity.detail

import com.junemon.junemoncase.base.BaseView
import com.junemon.junemoncase.model.AllCasingModel

/**
 *
Created by Ian Damping on 18/04/2019.
Github = https://github.com/iandamping
 */
interface DetailActivityView : BaseView {
    fun onFailGetData()
    fun onSuccesGetData(data: AllCasingModel?)
}