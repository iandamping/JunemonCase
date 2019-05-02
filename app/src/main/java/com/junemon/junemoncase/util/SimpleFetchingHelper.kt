package com.junemon.junemoncase.util

import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 *
Created by Ian Damping on 02/05/2019.
Github = https://github.com/iandamping
 */

inline fun <reified T, reified U> Single<T>.zipWith(other: SingleSource<U>): Single<Pair<T, U>> =
        zipWith(other, BiFunction { firstData, secondData -> Pair(firstData, secondData) })


inline fun <reified T> CompositeDisposable.executes(obs: Single<T>, crossinline onFailed: (Throwable) -> Unit, crossinline onSuccess: (T?) -> Unit) {
    this.add(obs.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
        onSuccess(it)
    }, {
        onFailed(it)
    }))
}


inline fun <reified T> Call<T>.executes(crossinline onFailure: (Throwable) -> Unit, crossinline onResponse: (T?) -> Unit) {
    this.enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            onFailure(t)
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            onResponse(response.body())
        }

    })
}


