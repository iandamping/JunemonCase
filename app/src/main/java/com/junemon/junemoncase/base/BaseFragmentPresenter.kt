package com.junemon.junemoncase.base

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import com.junemon.junemoncase.R
import org.jetbrains.anko.layoutInflater

/**
 *
Created by Ian Damping on 25/03/2019.
Github = https://github.com/iandamping
 */
abstract class BaseFragmentPresenter : BaseFragmentPresenterHelper {
    private lateinit var alert: AlertDialog

    protected fun setBaseDialog(ctx: Context?) {
        val dialogBuilder = ctx?.let { AlertDialog.Builder(it) }
        val inflater = ctx?.layoutInflater
        val dialogView = inflater?.inflate(R.layout.custom_loading, null)

        dialogBuilder?.setView(dialogView)
        alert = dialogBuilder?.create()!!
        alert.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alert.setCancelable(false)
        alert.setCanceledOnTouchOutside(false)

    }

    protected fun setDialogShow(status: Boolean) {
        if (!status) {
            alert.show()
        } else {
            alert.dismiss()
        }
    }
}