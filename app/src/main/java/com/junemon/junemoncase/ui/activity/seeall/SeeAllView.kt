package com.junemon.junemoncase.ui.activity.seeall

import com.junemon.junemoncase.base.BaseView
import com.junemon.junemoncase.model.AllCasingModel

/**
 *
Created by Ian Damping on 18/04/2019.
Github = https://github.com/iandamping
 */
interface SeeAllView : BaseView {
    fun onFailGetData()
    fun onSuccesGetData(data: List<AllCasingModel>?)
}