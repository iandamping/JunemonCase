package com.junemon.junemoncase.ui.fragment.profile

import com.junemon.junemoncase.base.BaseFragmentView
import com.junemon.junemoncase.model.UserProfile

/**
 *
Created by Ian Damping on 18/04/2019.
Github = https://github.com/iandamping
 */
interface ProfileView : BaseFragmentView {
    fun onSuccessGetData(data: UserProfile?)
    fun onFailedGetData()
}