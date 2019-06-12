package com.junemon.junemoncase.ui.activity.ordercasing

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.ian.app.helper.util.*
import com.junemon.junemoncase.JunemonApps
import com.junemon.junemoncase.JunemonApps.Companion.phoneTypeDatabaseReference
import com.junemon.junemoncase.R
import com.junemon.junemoncase.model.AllCasingModel
import com.junemon.junemoncase.model.UserProfileModel
import com.junemon.junemoncase.ui.activity.MainActivity
import com.junemon.junemoncase.util.Constant.sendDetailToOrder
import com.junemon.junemoncase.util.Constant.sendPhoneTypetoOrder
import com.junemon.junemoncase.util.alertHelper
import com.junemon.junemoncase.util.alertHelperToEditProfile
import com.junemon.junemoncase.util.alertHelperToLoginActivity
import kotlinx.android.synthetic.main.activity_order.*

/**
 *
Created by Ian Damping on 24/04/2019.
Github = https://github.com/iandamping
 */
class OrderCaseActivity : AppCompatActivity(), OrderCaseView {
    private var phoneType: String? = null
    private lateinit var casingData: AllCasingModel
    private lateinit var profileData: UserProfileModel

    private var arraySpinnerTypeCaseAdapter: ArrayAdapter<String>? = null

    private lateinit var presenter: OrderCasePresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_order)
        presenter = OrderCasePresenter(phoneTypeDatabaseReference).apply {
            attachView(this@OrderCaseActivity, this@OrderCaseActivity)
            onCreate()
        }
        onNewIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        presenter.setInitialData(intent?.getStringExtra(sendDetailToOrder))
        presenter.onGetPhoneTypeData(intent?.getStringExtra(sendPhoneTypetoOrder))
    }

    override fun initView() {
        setupToolbar()
        etOrderCasingTypeHp.setText(intent?.getStringExtra(sendPhoneTypetoOrder))
        btnOrderCasingCase.setOnClickListener {
            phoneType = etOrderCasingTypeHp.text.toString().trim()
            presenter.onUploadOrderCase(JunemonApps.mAllOrderDatabaseReference, casingData, profileData, phoneType)
        }
    }

    override fun onGetDetailedData(data: AllCasingModel?) {
        data?.let { nonNullData ->
            this.casingData = nonNullData
        }
        ivOrderCasing.loadWithGlide(data?.photoUrl)
        ivOrderCasing.setOnClickListener {
            fullScreen(data?.photoUrl)
        }
    }

    override fun onSuccessGetListPhoneType(data: List<String>?) {
        arraySpinnerTypeCaseAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
        arraySpinnerTypeCaseAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerOrderCasingTypeHp.adapter = arraySpinnerTypeCaseAdapter
        spinnerOrderCasingTypeHp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                casingData.casingType = data?.get(position)
            }

        }
    }

    private fun setupToolbar() {
        OrderCasingToolbar.setTitleTextColor(resources.getColor(R.color.black))
        OrderCasingToolbar.setSubtitleTextColor(resources.getColor(R.color.black))
        OrderCasingToolbar.title = getString(R.string.app_name)
        setSupportActionBar(OrderCasingToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onFailedGetListPhoneType(msg: String?) {
        logE(msg)
    }

    override fun onGetUserData(data: UserProfileModel) {
        this.profileData = data
        etOrderCasingName.setText(data.nameUser)
        etOrderCasingAddress.setText(data.addressUser)
        etOrderCasingKecamatan.setText(data.provinceUser)
        etOrderCasingKota.setText(data.cityUser)
        etOrderCasingPhoneNumber.setText(data.phoneNumberUser)

    }

    override fun onLoginFirst() {
        alertHelperToLoginActivity(resources.getString(R.string.login_first))
    }

    override fun onEditUserFirst() {
        alertHelperToEditProfile(getString(R.string.edit_profile_first))
    }

    override fun onSuccessOrderCase() {
        startActivity<MainActivity>()
    }

    override fun onFailOrderCase() {
        alertHelper(resources.getString(R.string.error_happen))
    }

}