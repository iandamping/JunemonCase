package com.junemon.junemoncase.ui.activity.upload

import android.content.Context
import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.junemon.junemoncase.R
import com.junemon.junemoncase.base.BasePresenter
import com.junemon.junemoncase.model.AllCasingModel


/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */
class UploadPresenter(private val mView: UploadView) : BasePresenter() {
    private var uploadTask: UploadTask? = null
    private var ctx: Context? = null
    override fun onCreate(context: Context) {
        this.ctx = context
        setBaseDialog(context)
        getCasingType()
        mView.initView()
    }

    private fun getCasingType() {
        if (ctx != null) {
            mView.onGetTypeCasing(ctx?.resources?.getStringArray(R.array.custom_case)?.toMutableList())
        }
    }

    fun uploadToFirebase(
            storageRef: StorageReference,
            dataRef: DatabaseReference,
            selectedImage: Uri?,
            data: AllCasingModel?
    ) {
        setDialogShow(false)
        val reference = selectedImage?.lastPathSegment?.let { storageRef.child(it) }
        uploadTask = reference?.putFile(selectedImage).also {
            it?.addOnSuccessListener {
                reference?.downloadUrl?.addOnSuccessListener { dataReferenceResult ->
                    data?.photoUrl = dataReferenceResult.toString()
                    dataRef.push().setValue(data).addOnCompleteListener { status ->
                        when {
                            status.isSuccessful -> {
                                setDialogShow(true)
                                mView.onSuccesUploadData()
                            }
                            status.isCanceled -> {
                                setDialogShow(true)
                                mView.onFailUploadData(ctx?.getString(R.string.error_happen))
                            }
                        }
                    }
                }
            }
        }

    }

}