package com.junemon.junemoncase.ui.activity.uploadphonetype

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.junemon.junemoncase.JunemonApps.Companion.phoneTypeDatabaseReference
import com.junemon.junemoncase.R
import com.junemon.junemoncase.util.*
import kotlinx.android.synthetic.main.activity_upload_phonetype.*

/**
 *
Created by Ian Damping on 22/04/2019.
Github = https://github.com/iandamping
 */
class UploadPhoneTypeActivity : AppCompatActivity(), UploadPhoneTypeView {
    private lateinit var presenter: UploadPhoneTypePresenter
    private var autoTextAdapter: ArrayAdapter<String>? = null
    private var tmpListData: MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_upload_phonetype)
        presenter = UploadPhoneTypePresenter(this)
        presenter.onCreate(this)

    }

    override fun onSuccessUploadPhoneType() {
        startActivity<UploadPhoneTypeActivity>()
    }

    override fun onFailedUploadPhoneType() {
        logE("Upload phone type error")
    }

    override fun initView() {
        autoTextAdapter =
                ArrayAdapter(this, android.R.layout.simple_list_item_1, resources.getStringArray(R.array.all_phone_names))
        etPhoneType.setAdapter(autoTextAdapter)
        etPhoneType.threshold = 1
        btnUnggahTipeHp.setOnClickListener {
            val typePhone = etPhoneType.text.toString().trim()
            if (typePhone.isBlank()) {
                etPhoneType.requestError(getString(R.string.not_null))
            } else if (tmpListData.size == 0) {
                myToast(getString(R.string.not_null))
            }
            if (!typePhone.isBlank() && tmpListData.size != 0) {
                presenter.uploadPhone(phoneTypeDatabaseReference, typePhone, tmpListData)
            }
        }
    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked = view.isChecked

            when (view.id) {
                R.id.cbHardcase -> {
                    if (checked) {
                        tmpListData.add("Hardcase")
                    } else {
                        tmpListData.remove("Hardcase")
                    }
                }
                R.id.cbSoftcase -> {
                    if (checked) {
                        tmpListData.add("Softcase")
                    } else {
                        tmpListData.remove("Softcase")

                    }
                }
                R.id.cbSoftglossy -> {
                    if (checked) {
                        tmpListData.add("Softglossy")
                    } else {
                        tmpListData.remove("Softglossy")

                    }
                }
                R.id.cbSoftblack -> {
                    if (checked) {
                        tmpListData.add("Softblack")
                    } else {
                        tmpListData.remove("Softblack")

                    }
                }
                R.id.cbSoftcrack -> {
                    if (checked) {
                        tmpListData.add("Softcrack")
                    } else {
                        tmpListData.remove("Softcrack")

                    }
                }
                R.id.cbHardcrack -> {
                    if (checked) {
                        tmpListData.add("Hardcrack")
                    } else {
                        tmpListData.remove("Hardcrack")

                    }
                }
                R.id.cbPremiumSoft -> {
                    if (checked) {
                        tmpListData.add("Premium Soft")
                    } else {
                        tmpListData.remove("Premium Soft")
                    }
                }
                R.id.cbPremium -> {
                    if (checked) {
                        tmpListData.add("Premium")
                    } else {
                        tmpListData.remove("Premium")

                    }
                }
                R.id.cbSoftDiamond -> {
                    if (checked) {
                        tmpListData.add("Soft Diamond")
                    } else {
                        tmpListData.remove("Soft Diamond")
                    }
                }
                R.id.cbFlip -> {
                    if (checked) {
                        tmpListData.add("Flip 2in1")
                    } else {
                        tmpListData.remove("Flip 2in1")
                    }
                }
            }
        }
    }

}