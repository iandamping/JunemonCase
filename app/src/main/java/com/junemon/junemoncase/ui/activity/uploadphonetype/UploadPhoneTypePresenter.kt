package com.junemon.junemoncase.ui.activity.uploadphonetype

import android.content.Context
import com.google.firebase.database.DatabaseReference
import com.junemon.junemoncase.base.BasePresenter
import com.junemon.junemoncase.model.PhoneType

/**
 *
Created by Ian Damping on 22/04/2019.
Github = https://github.com/iandamping
 */
class UploadPhoneTypePresenter(private val mView: UploadPhoneTypeView) : BasePresenter() {
    private var listData: MutableMap<String, PhoneType> = mutableMapOf()
    override fun onCreate(context: Context) {
        setBaseDialog(context)
        mView.initView()
    }

    fun uploadPhone(dataRef: DatabaseReference, phoneType: String, listOfReadyCase: List<String>) {
        setDialogShow(false)
        listData[phoneType] = PhoneType(listOfReadyCase)
        dataRef.push().setValue(listData).addOnCompleteListener {
            if (it.isSuccessful) {
                setDialogShow(true)
                mView.onSuccessUploadPhoneType()
            }
        }
    }
}