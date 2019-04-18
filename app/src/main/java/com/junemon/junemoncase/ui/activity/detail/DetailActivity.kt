package com.junemon.junemoncase.ui.activity.detail

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.junemon.junemoncase.R
import com.junemon.junemoncase.model.AllCasingModel
import com.junemon.junemoncase.util.Constant.seeDetailKey
import com.junemon.junemoncase.util.fullScreenAnimation
import com.junemon.junemoncase.util.loadUrl
import com.junemon.junemoncase.util.loadUrlFullScreen
import kotlinx.android.synthetic.main.activity_detail_casing.*
import kotlinx.android.synthetic.main.activity_fullscreen.*

/**
 *
Created by Ian Damping on 18/04/2019.
Github = https://github.com/iandamping
 */
class DetailActivity : AppCompatActivity(), DetailActivityView {
    private lateinit var presenter: DetailActivityPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_detail_casing)
        presenter = DetailActivityPresenter(this)
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
        tvDetailPenjelasanCase
    }

    override fun initView() {
        setupToolbar()
    }


    private fun setupToolbar() {
        DetailCasingToolbar.setTitleTextColor(resources.getColor(R.color.black))
        DetailCasingToolbar.setSubtitleTextColor(resources.getColor(R.color.black))
        DetailCasingToolbar.title = "Junemon Case"
        setSupportActionBar(DetailCasingToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}