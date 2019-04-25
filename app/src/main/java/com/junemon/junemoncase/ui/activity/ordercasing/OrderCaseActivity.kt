package com.junemon.junemoncase.ui.activity.ordercasing

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.junemon.junemoncase.JunemonApps.Companion.phoneTypeDatabaseReference
import com.junemon.junemoncase.R
import com.junemon.junemoncase.model.AllCasingModel
import com.junemon.junemoncase.util.Constant.sendDetailToOrder
import com.junemon.junemoncase.util.Constant.sendPhoneTypetoOrder
import com.junemon.junemoncase.util.fullScreenAnimation
import com.junemon.junemoncase.util.loadUrl
import com.junemon.junemoncase.util.loadUrlFullScreen
import com.junemon.junemoncase.util.myToast
import kotlinx.android.synthetic.main.activity_fullscreen.*
import kotlinx.android.synthetic.main.activity_order.*

/**
 *
Created by Ian Damping on 24/04/2019.
Github = https://github.com/iandamping
 */
class OrderCaseActivity : AppCompatActivity(), OrderCaseView {
    private var arraySpinnerTypeCaseAdapter: ArrayAdapter<String>? = null
    private var arraySpinnerProvinceAdapter: ArrayAdapter<String>? = null

    private lateinit var presenter: OrderCasePresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_order)
        presenter = OrderCasePresenter(phoneTypeDatabaseReference, this, this)
        presenter.onCreate(this)
        onNewIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        presenter.setInitialData(intent?.getStringExtra(sendDetailToOrder))
        presenter.onGetPhoneTypeData(intent?.getStringExtra(sendPhoneTypetoOrder))
    }

    override fun initView() {
        etOrderCasingTypeHp.setText(intent?.getStringExtra(sendPhoneTypetoOrder))
    }

    override fun onGetDetailedData(data: AllCasingModel?) {
        ivOrderCasing.loadUrl(data?.photoUrl)
        ivOrderCasing.setOnClickListener {
            val alert = Dialog(this, R.style.AppTheme)
            alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alert.setContentView(R.layout.activity_fullscreen)
            alert.setCanceledOnTouchOutside(true)
            alert.fullScreenImageView.loadUrlFullScreen(data?.photoUrl)
            alert.show()
            alert.ivClose.setOnClickListener {
                alert.dismiss()
            }
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
            }

        }
    }

    override fun onFailedGetListPhoneType(msg: String?) {
        myToast(msg)
    }
}