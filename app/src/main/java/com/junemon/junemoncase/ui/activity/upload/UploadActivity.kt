package com.junemon.junemoncase.ui.activity.upload

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.junemon.junemoncase.JunemonApps.Companion.mAllImageDatabaseReference
import com.junemon.junemoncase.JunemonApps.Companion.storageDatabaseReference
import com.junemon.junemoncase.R
import com.junemon.junemoncase.model.AllCasingModel
import com.junemon.junemoncase.ui.activity.MainActivity
import com.junemon.junemoncase.util.*
import com.junemon.junemoncase.util.Constant.RequestSelectGalleryImage
import com.junemon.junemoncase.util.Constant.switchBackToMain
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_fullscreen.*
import kotlinx.android.synthetic.main.activity_upload.*

/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */
class UploadActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, UploadView {
    private var tmpTypeCase: String? = null
    private var tmpIsBestSeller: Boolean? = false
    private var allCategory: MutableList<String>? = mutableListOf()
    private var arraySpinnerAdapter: ArrayAdapter<String>? = null
    private var isPermissionGranted = false
    private var selectedUriForFirebase: Uri? = null
    private lateinit var presenter: UploadPresenter
    private lateinit var data: AllCasingModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_upload)
        getAllPermisions()
        presenter = UploadPresenter(this)
        presenter.onCreate(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity<MainActivity>() {
            putExtra(switchBackToMain, "1")
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RequestSelectGalleryImage) {
                selectedUriForFirebase = data?.data
                val bitmap = ImageHelper.getBitmapFromGallery(this, selectedUriForFirebase)
                if (bitmap != null) {
                    ivPickPhoto.visible()
                    ivPickPhoto.setImageBitmap(bitmap)
                    ivPickPhoto.setOnClickListener {
                        val alert = Dialog(this, R.style.AppTheme)
                        alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        alert.setContentView(R.layout.activity_fullscreen)
                        alert.setCanceledOnTouchOutside(true)
                        alert.fullScreenImageView.setImageBitmap(bitmap)
                        alert.show()
                        alert.ivClose.setOnClickListener {
                            alert.dismiss()
                        }
                    }
                }
            }
        }
    }

    private fun openImageFromGallery(status: Boolean?) {
        if (status != null) {
            if (status) {
                val intents = Intent(Intent.ACTION_PICK)
                intents.type = "image/*"
                startActivityForResult(intents, RequestSelectGalleryImage)
            } else {
                alertHelper(resources.getString(R.string.permission_not_granted))
            }
        }
    }

    private fun getAllPermisions() {
        Dexter.withActivity(this).withPermissions(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                if (report?.areAllPermissionsGranted()!!) {
                    isPermissionGranted = true

                }
            }

            override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                    token: PermissionToken?
            ) {
            }

        }).check()
    }

    override fun onFailUploadData(msg: String?) {
        logD(msg)
    }

    override fun onSuccesUploadData() {
        startActivity<MainActivity>().also { finish() }
    }

    override fun onGetTypeCasing(data: List<String>?) {
        allCategory = data as MutableList<String>
        spinnerTypeCasing!!.onItemSelectedListener = this
        arraySpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, allCategory)
        arraySpinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTypeCasing.adapter = arraySpinnerAdapter
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        tmpTypeCase = allCategory?.get(position)
    }


    override fun initView() {
        btnUnggahFoto.setOnClickListener {
            openImageFromGallery(isPermissionGranted)
        }

        btnUnggah.setOnClickListener {
            when {
                selectedUriForFirebase == null -> openImageDialogFromSnackbar(rootUploadPage)
                tmpTypeCase == null -> myToast(getString(R.string.pilih_tipe_case))
                tmpIsBestSeller == null -> myToast(getString(R.string.pilih_tipe_bes_seller))
                else -> {
                    initRadioButton()
                    data = AllCasingModel(tmpTypeCase, tmpIsBestSeller, null, selectedUriForFirebase?.lastPathSegment)
                    presenter.uploadToFirebase(
                            storageDatabaseReference,
                            mAllImageDatabaseReference,
                            selectedUriForFirebase,
                            data
                    )
                }
            }

        }
    }

    private fun initRadioButton() {
        if (rbBestSeller.isChecked) {
            tmpIsBestSeller = true
        }

        if (rbNotBestSeller.isChecked) {
            tmpIsBestSeller = false
        }
    }

    private fun openImageDialogFromSnackbar(views: View) {
        val snackbar = Snackbar
                .make(views, getString(R.string.pilih_gambar_dulu), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.snackbar_buka)) {
                    openImageFromGallery(isPermissionGranted)
                }
        snackbar.show()
    }

}