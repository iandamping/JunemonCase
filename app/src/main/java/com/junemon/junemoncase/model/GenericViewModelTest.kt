package com.junemon.junemoncase.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

/**
 *
Created by Ian Damping on 15/05/2019.
Github = https://github.com/iandamping
 */
class GenericViewModelTest<T> : ViewModel() {
    private var customData: MutableLiveData<T> = MutableLiveData()

    fun setCustomData(data: T) {
        customData.value = data
    }

    fun customizeOutput() {
        /*Change into new livedata*/
        var newCustomData = Transformations.map(customData) {

        }
    }

    fun getCustomData(): MutableLiveData<T> = customData
}