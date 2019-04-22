package com.junemon.junemoncase.util

import android.widget.EditText
/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */
fun EditText.requestError(message: String?) {
    if (this.text.isNullOrEmpty()) {
        this.requestFocus()
        this.error = message
    }

}