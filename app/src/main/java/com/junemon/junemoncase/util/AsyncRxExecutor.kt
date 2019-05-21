package com.junemon.junemoncase.util

import com.junemon.junemoncase.BuildConfig
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 *
Created by Ian Damping on 16/05/2019.
Github = https://github.com/iandamping
 */
fun CompositeDisposable.asyncRxExecutor(heavyFunction: () -> Unit?) {
    this.add(Observable.fromCallable(Runnable {
        heavyFunction()
    }::run).subscribeOn(Schedulers.io()).subscribe({
            logD(Constant.succesWork)
    }, {
            logE(Constant.failedWork)
    }))

}