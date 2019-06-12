package com.junemon.junemoncase.base

import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import com.ian.app.helper.util.layoutInflater
import com.junemon.junemoncase.R
import io.reactivex.disposables.CompositeDisposable

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
abstract class BasePresenter : BasePresenterHelper {
    private lateinit var alert: AlertDialog
    protected val compose: CompositeDisposable = CompositeDisposable()
    protected fun setBaseDialog(ctx: Context) {
        val dialogBuilder = AlertDialog.Builder(ctx)
        val inflater = ctx.layoutInflater
        val dialogView = inflater.inflate(R.layout.custom_loading, null)

        dialogBuilder.setView(dialogView)
        alert = dialogBuilder.create()
        alert.window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
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