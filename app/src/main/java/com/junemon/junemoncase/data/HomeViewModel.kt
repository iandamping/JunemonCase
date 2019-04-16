package com.junemon.junemoncase.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.junemon.junemoncase.model.AllCasingModel

/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */
class HomeViewModel : ViewModel() {
    private var imageData: MutableLiveData<AllCasingModel>? = MutableLiveData()

    fun setCasingData(data: AllCasingModel) {
        this.imageData?.value = data
    }

    fun getCasingData(): MutableLiveData<AllCasingModel>? = imageData
}