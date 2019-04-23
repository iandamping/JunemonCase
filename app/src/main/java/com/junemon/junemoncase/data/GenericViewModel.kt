package com.junemon.junemoncase.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */
class GenericViewModel<T> : ViewModel() {
    private var customData: MutableLiveData<T> = MutableLiveData()

    fun setGenericData(data: T) {
        this.customData.value = data
    }

    fun getGenericData(): MutableLiveData<T> = customData
}