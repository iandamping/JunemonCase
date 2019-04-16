package com.junemon.junemoncase.util

import androidx.fragment.app.FragmentActivity
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */
fun FragmentActivity.alertHelper(tittle: String?) {
    tittle?.let {
        this.alert(it) {
            yesButton {
                it.dismiss()
                this@alertHelper.finish()
            }
        }.show()
    }
}
