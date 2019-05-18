package com.junemon.junemoncase.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel

/**
 *
Created by Ian Damping on 13/05/2019.
Github = https://github.com/iandamping
 */
class GenericViewModelZipperTriple<A,B,C>(a: LiveData<A>?, b: LiveData<B>?, c: LiveData<C>?) : ViewModel() {
    private var customData: MediatorLiveData<Triple<A?, B?,C?>> = MediatorLiveData()
    private var lastA: A? = null
    private var lastB: B? = null
    private var lastC: C? = null

    /*Be careful this function customize to get & pass null values*/
    private fun update() {
        val localLastA = lastA
        val localLastB = lastB
        val localLastC = lastC
        customData.value = Triple(localLastA, localLastB,localLastC)
    }

    init {
        a?.let { nonNullLiveData ->
            customData.addSource(nonNullLiveData) {
                it?.let { nonNullA ->
                    lastA = nonNullA
                    update()
                }
            }
        }
        b?.let { nonNullLiveData ->
            customData.addSource(nonNullLiveData) {
                it?.let { nonNullB ->
                    lastB = nonNullB
                    update()
                }

            }
        }

        c?.let { nonNullLiveData ->
            customData.addSource(nonNullLiveData) {
                it?.let { nonNullC ->
                    lastC = nonNullC
                    update()
                }

            }
        }
    }

    fun getGenericData(): MediatorLiveData<Triple<A?, B?,C?>> = customData
}