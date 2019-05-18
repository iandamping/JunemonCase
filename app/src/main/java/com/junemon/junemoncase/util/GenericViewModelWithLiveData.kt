package com.junemon.junemoncase.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

/**
 *
Created by Ian Damping on 13/05/2019.
Github = https://github.com/iandamping
 */
class GenericViewModelWithLiveData<T>(a: LiveData<T>?) : ViewModel() {
    private var genericData: LiveData<T>? = a

    fun getGenericViewModelData(): LiveData<T>? = this.genericData
}