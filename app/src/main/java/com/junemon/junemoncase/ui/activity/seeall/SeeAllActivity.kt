package com.junemon.junemoncase.ui.activity.seeall

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ian.recyclerviewhelper.helper.setUpWithGrid
import com.junemon.junemoncase.JunemonApps
import com.junemon.junemoncase.R
import com.junemon.junemoncase.model.AllCasingModel
import com.junemon.junemoncase.ui.activity.detail.DetailActivity
import com.junemon.junemoncase.util.*
import com.junemon.junemoncase.util.Constant.seeAllKey
import kotlinx.android.synthetic.main.activity_see_all.*
import kotlinx.android.synthetic.main.item_see_all.*
import kotlinx.android.synthetic.main.item_see_all.view.*

/**
 *
Created by Ian Damping on 18/04/2019.
Github = https://github.com/iandamping
 */
class SeeAllActivity : AppCompatActivity(), SeeAllView {
    private lateinit var presenter: SeeAllPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_see_all)
        presenter = SeeAllPresenter(this)
        presenter.onCreate(this)
        onNewIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        presenter.onGetPassedData(intent?.getStringExtra(seeAllKey))
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

    override fun onFailGetData() {
    }

    override fun onSuccesGetData(data: List<AllCasingModel>?) {
        shimmerSeeAll?.stopShimmer()
        shimmerSeeAll?.gone()
        data?.let { caseData ->
            rvSeeAll.setUpWithGrid(caseData, R.layout.item_see_all, 2, {
                ivCasingSeeAll.loadUrl(it.photoUrl)
                tvCasingSeeAllType.text = it.casingType
            }, {
                startActivity<DetailActivity> {
                    putExtra(Constant.seeDetailKey, JunemonApps.gson.toJson(this@setUpWithGrid))
                }
            })
        }
    }

    override fun initView() {
        setupToolbar()
    }

    override fun onPause() {
        super.onPause()
        shimmerSeeAll?.stopShimmer()
    }

    override fun onResume() {
        super.onResume()
        shimmerSeeAll?.startShimmer()
    }

    private fun setupToolbar() {
        detailSeeAllToolbar.setTitleTextColor(resources.getColor(R.color.black))
        detailSeeAllToolbar.setSubtitleTextColor(resources.getColor(R.color.black))
        detailSeeAllToolbar.title = "See All"
        setSupportActionBar(detailSeeAllToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}