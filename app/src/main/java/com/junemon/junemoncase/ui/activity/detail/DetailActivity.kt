package com.junemon.junemoncase.ui.activity.detail

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.junemon.junemoncase.JunemonApps.Companion.gson
import com.junemon.junemoncase.JunemonApps.Companion.phoneTypeDatabaseReference
import com.junemon.junemoncase.R
import com.junemon.junemoncase.model.AllCasingModel
import com.junemon.junemoncase.ui.activity.ordercasing.OrderCaseActivity
import com.junemon.junemoncase.util.*
import com.junemon.junemoncase.util.Constant.seeDetailKey
import com.junemon.junemoncase.util.Constant.sendDetailToOrder
import com.junemon.junemoncase.util.Constant.sendPhoneTypetoOrder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_casing.*
import kotlinx.android.synthetic.main.activity_fullscreen.*

/**
 *
Created by Ian Damping on 18/04/2019.
Github = https://github.com/iandamping
 */
class DetailActivity : AppCompatActivity(), DetailActivityView {
    private var isPhoneAvailable: Boolean? = null
    private var phoneType: String? = null
    private var autoTextAdapter: ArrayAdapter<String>? = null
    private var dataPassing: AllCasingModel? = null
    private lateinit var presenter: DetailActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_detail_casing)
        presenter = DetailActivityPresenter(phoneTypeDatabaseReference, this, this)
        presenter.onCreate(this)
        onNewIntent(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        presenter.onGetDataPassed(intent?.getStringExtra(seeDetailKey))
    }

    override fun onFailGetData() {
    }

    override fun onSuccesGetData(data: AllCasingModel?) {
        ivDetailCasing.loadUrl(data?.photoUrl)
        ivDetailCasing.setOnClickListener {
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
        tvDetailJenisCase.text = data?.casingType
        when {
            data?.casingType?.equals("Hardcase")!! -> tvDetailPenjelasanCase.text =
                    getString(R.string.spesifikasi_hardcase)
            data.casingType?.equals("Softcase")!! -> tvDetailPenjelasanCase.text =
                    getString(R.string.spesifikasi_softcase)
            data.casingType?.equals("Softcrack")!! -> tvDetailPenjelasanCase.text =
                    getString(R.string.spesifikasi_softcrack)
            data.casingType?.equals("Premium")!! -> tvDetailPenjelasanCase.text =
                    getString(R.string.spesifikasi_premium)
            data.casingType?.equals("Premium Soft")!! -> tvDetailPenjelasanCase.text =
                    getString(R.string.spesifikasi_premium_soft)
        }
        this.dataPassing = data
    }

    override fun initView() {
        setupToolbar()
        val listCase = resources.getStringArray(R.array.all_phone_names).toMutableList()

        autoTextAdapter =
                ArrayAdapter(this, android.R.layout.simple_list_item_1, resources.getStringArray(R.array.all_phone_names))
        etDetailCheckCase.setAdapter(autoTextAdapter)
        etDetailCheckCase.threshold = 1

        btnDetailCheckCase.setOnClickListener {
            phoneType = etDetailCheckCase.text.toString().trim()
            when {
                phoneType.isNullOrBlank() -> {
                    etDetailCheckCase.requestError(getString(R.string.not_null))
                }
                else -> {
                    Observable.fromIterable(listCase).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                when {
                                    it != phoneType -> {
                                        tvDetailPhoneNotReady.visible()
                                    }
                                    it == phoneType -> {
                                        tvDetailPhoneNotReady.gone()
                                        presenter.onGetPhoneTypeData(phoneType)
                                    }
                                }
                            }
                }
            }
        }

    }


    private fun setupToolbar() {
        DetailCasingToolbar.setTitleTextColor(resources.getColor(R.color.black))
        DetailCasingToolbar.setSubtitleTextColor(resources.getColor(R.color.black))
        DetailCasingToolbar.title = "Junemon Case"
        setSupportActionBar(DetailCasingToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSuccessPhoneAvailable(data: Boolean?) {
        if (data != null) {
            this.isPhoneAvailable = data
        }
        isPhoneAvailable?.let {
            when {
                it -> {
                    tvDetailPhoneNotReady.gone()
                    tvDetailPhoneReady.visible()
                    btnDetailOrder.background = resources.getDrawable(R.drawable.button_upload_bg_blue)
                    btnDetailOrder.text = resources.getString(R.string.order_sekarang)
                    btnDetailOrder.setOnClickListener {
                        startActivity<OrderCaseActivity> {
                            putExtra(sendDetailToOrder, gson.toJson(dataPassing))
                            putExtra(sendPhoneTypetoOrder, phoneType)
                            finish()
                        }
                    }
                }
            }
        }

    }


}