package com.junemon.junemoncase.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 *
Created by Ian Damping on 18/04/2019.
Github = https://github.com/iandamping
 */

inline fun <reified T> Gson.fromJsonHelper(data: String) = this.fromJson<T>(data, object : TypeToken<T>() {}.type)