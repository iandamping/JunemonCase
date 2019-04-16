package com.junemon.junemoncase.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.junemon.junemoncase.R

/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */
inline fun <reified T : Activity> FragmentActivity.startActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    intent.init()
    startActivity(intent, options)
}

inline fun <reified T : Activity> Context.startActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    intent.init()
    startActivity(intent, options)
}

inline fun <reified T : Any> newIntent(ctx: Context): Intent = Intent(ctx, T::class.java)

fun FragmentManager.switchFragment(savedInstanceState: Bundle?, target: Fragment) {
    if (savedInstanceState == null) {
        this.beginTransaction().replace(R.id.main_container, target).commit()
    }
}
