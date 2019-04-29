package com.junemon.junemoncase.util

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *
Created by Ian Damping on 26/04/2019.
Github = https://github.com/iandamping
 */

fun <T> Call<T>.executes(onFailure: (Throwable) -> Unit, onResponse: (T?) -> Unit) {
    this.enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            onFailure(t)
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            onResponse(response.body())
        }

    })
}