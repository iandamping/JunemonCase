package com.junemon.junemoncase.ui.fragment.home

import com.junemon.junemoncase.base.BaseFragmentView
import com.junemon.junemoncase.model.AllCasingModel

/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */
interface HomeView : BaseFragmentView {
    fun onFailGetData(msg: String?)
    fun onSuccesGetData(data: AllCasingModel)
}