package com.junemon.junemoncase.util

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.ian.app.helper.util.startActivity
import com.junemon.junemoncase.R
import com.junemon.junemoncase.ui.activity.MainActivity
import com.junemon.junemoncase.ui.activity.editprofile.EditProfileActivity
import kotlinx.android.synthetic.main.custom_failed_dialog.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.layoutInflater
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

fun Context.alertHelperToEditProfile(tittle: String?) {
    val dialogBuilder = AlertDialog.Builder(this)
    val inflater = this.layoutInflater
    val dialogView = inflater.inflate(R.layout.custom_failed_dialog, null)
    dialogView.tvAlertMessage.text = tittle
    dialogBuilder.setPositiveButton("Oke") { dialog, _ ->
        dialog.dismiss()
        startActivity<EditProfileActivity>()
    }

    dialogBuilder.setView(dialogView)
    val alert = dialogBuilder.create()
    alert.setCancelable(false)
    alert.show()
}

fun Context.alertHelperToLoginActivity(tittle: String?) {
    val dialogBuilder = AlertDialog.Builder(this)
    val inflater = this.layoutInflater
    val dialogView = inflater.inflate(R.layout.custom_failed_dialog, null)
    dialogView.tvAlertMessage.text = tittle
    dialogBuilder.setPositiveButton("Oke") { dialog, _ ->
        dialog.dismiss()
        startActivity<MainActivity> {
            putExtra(Constant.switchBackToMain, "4")
        }
    }

    dialogBuilder.setView(dialogView)
    val alert = dialogBuilder.create()
    alert.setCancelable(false)
    alert.show()
}
