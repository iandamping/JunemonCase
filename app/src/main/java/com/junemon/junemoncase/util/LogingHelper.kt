package com.junemon.junemoncase.util

import android.util.Log

/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */
inline fun <reified T> T.logE(msg: String?) {
    val tag = T::class.java.simpleName
    Log.e(tag, msg)
}

inline fun <reified T> T.logD(msg: String?) {
    val tag = T::class.java.simpleName
    Log.d(tag, msg)
}