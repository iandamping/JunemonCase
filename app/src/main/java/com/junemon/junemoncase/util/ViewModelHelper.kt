package com.junemon.junemoncase.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders


/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */
inline fun <reified T : ViewModel> FragmentActivity.viewModelHelperForActivity(): T {
    return ViewModelProviders.of(this).get(T::class.java)
}


inline fun <reified T : ViewModel> Fragment.viewModelHelperForFragment(): T {
    return ViewModelProviders.of(this).get(T::class.java)
}

inline fun <reified T : ViewModel> Fragment.withViewModel(body: T.() -> Unit): T {
    val vm = viewModelHelperForFragment<T>()
    vm.body()
    return vm
}