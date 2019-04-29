package com.junemon.junemoncase.ui.activity.editprofile

import com.junemon.junemoncase.base.BaseView
import com.junemon.junemoncase.model.GeneralProvinceModel
import com.junemon.junemoncase.model.UserProfileModel

/**
 *
Created by Ian Damping on 26/04/2019.
Github = https://github.com/iandamping
 */
interface EditProfileView : BaseView {
    fun onGetUserData(data: UserProfileModel?)
    fun onGetProvinceData(data: List<GeneralProvinceModel>?)
    fun onGetCityData(data: List<GeneralProvinceModel>?)
    fun onFailGetRajaOngkirData(msg: String?)
    fun onFailEditProfile(msg: String?)
}