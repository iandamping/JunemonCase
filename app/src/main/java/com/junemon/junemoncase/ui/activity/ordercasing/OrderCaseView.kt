package com.junemon.junemoncase.ui.activity.ordercasing

import com.junemon.junemoncase.base.BaseView
import com.junemon.junemoncase.model.AllCasingModel
import com.junemon.junemoncase.model.UserProfileModel

/**
 *
Created by Ian Damping on 24/04/2019.
Github = https://github.com/iandamping
 */
interface OrderCaseView : BaseView {
    fun onGetDetailedData(data: AllCasingModel?)
    fun onGetUserData(data: UserProfileModel)
    fun onLoginFirst()
    fun onEditUserFirst()
    fun onSuccessGetListPhoneType(data: List<String>?)
    fun onFailedGetListPhoneType(msg: String?)
}