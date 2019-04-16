package com.junemon.junemoncase.ui.activity.upload

import com.junemon.junemoncase.base.BaseView

/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */
interface UploadView : BaseView {
    fun onFailUploadData(msg: String?)
    fun onSuccesUploadData()
    fun onGetTypeCasing(data: List<String>?)
}