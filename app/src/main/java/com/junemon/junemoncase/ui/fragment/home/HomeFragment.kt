package com.junemon.junemoncase.ui.fragment.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.junemon.junemoncase.JunemonApps.Companion.mAllImageDatabaseReference
import com.junemon.junemoncase.R
import com.junemon.junemoncase.model.AllCasingModel
import com.junemon.junemoncase.ui.fragment.home.slideradapter.SliderItemAdapter
import com.junemon.junemoncase.util.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.item_homefragment.view.*

/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */
class HomeFragment : Fragment(), HomeView {
    private var mHandler: Handler? = null
    private var pageSize: Int? = 0
    private var currentPage = 0
    private val delayMillis = 4000L
    private lateinit var presenter: HomePresenter
    private var actualView: View? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = HomePresenter(mAllImageDatabaseReference, this, this)
        presenter.onAttach(context)
        mHandler = Handler()


    }

    override fun onFailGetData(msg: String?) {
        if (actualView != null) {
            actualView?.shimmer_home?.stopShimmer()
            actualView?.shimmer_home?.gone()
            logD(msg)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views = container?.inflates(R.layout.fragment_home)
        views?.let { presenter.onCreateView(it) }
        return views
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.onGetData()
    }

    override fun onSuccesGetBestSellerData(data: List<AllCasingModel>?) {
        pageSize = data?.size
        actualView?.vpBestSeller?.adapter = data?.let { SliderItemAdapter(it) }
        actualView?.indicator?.setViewPager(actualView?.vpBestSeller)
        if (mHandler != null) {
            mHandler?.removeCallbacks(slideRunnable)
        }
        mHandler?.postDelayed(slideRunnable, delayMillis)
    }

    private var slideRunnable: Runnable = object : Runnable {
        override fun run() {

            if (currentPage == pageSize) {
                currentPage = 0
            }
            actualView?.vpBestSeller?.setCurrentItem(currentPage++, true)
            mHandler!!.postDelayed(this, delayMillis)
        }
    }

    override fun onSuccesGetHardcaseData(data: List<AllCasingModel>?) {
        if (actualView != null) {
            actualView?.shimmer_home?.stopShimmer()
            actualView?.shimmer_home?.gone()
            data?.let {caseData ->
                actualView?.rvHardcase?.setUpHorizontal(caseData, R.layout.item_homefragment, {
                    ivHomeCasing.loadUrl(it.photoUrl)
                    tvHomeTypeCasing.text = it.casingType
                })
            }
        }
    }

    override fun onSuccesGetSoftcaseData(data: List<AllCasingModel>?) {
    }

    override fun onSuccesGetPremiumData(data: List<AllCasingModel>?) {
    }

    override fun onSuccesGetPremiumSoftData(data: List<AllCasingModel>?) {
    }

    override fun onSuccesGetAirBagData(data: List<AllCasingModel>?) {
        if (actualView!=null){
            data?.let { caseData ->
                actualView?.rvAirbag?.setUpHorizontal(caseData, R.layout.item_homefragment,{
                    ivHomeCasing.loadUrl(it.photoUrl)
                    tvHomeTypeCasing.text = it.casingType
                })
            }
        }
    }


    override fun initView(view: View) {
        this.actualView = view
    }

    override fun onStart() {
        super.onStart()
        mHandler?.postDelayed(slideRunnable, 4000)
    }

    override fun onStop() {
        super.onStop()
        mHandler?.removeCallbacks(slideRunnable)
    }

    override fun onPause() {
        super.onPause()
        actualView?.shimmer_home?.stopShimmer()
    }

    override fun onResume() {
        super.onResume()
        actualView?.shimmer_home?.startShimmer()
    }

}