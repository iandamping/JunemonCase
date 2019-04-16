package com.junemon.junemoncase.ui.fragment.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.junemon.junemoncase.JunemonApps.Companion.mAllImageDatabaseReference
import com.junemon.junemoncase.R
import com.junemon.junemoncase.model.AllCasingModel
import com.junemon.junemoncase.util.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.item_homefragment.view.*

/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */
class HomeFragment : Fragment(), HomeView {
    private lateinit var presenter: HomePresenter
    private var actualView: View? = null
    private var listAllData: MutableList<AllCasingModel> = mutableListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = HomePresenter(mAllImageDatabaseReference, this, this)
        presenter.onAttach(context)
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

    override fun onSuccesGetData(data: AllCasingModel) {
        listAllData.add(data)
        if (actualView != null) {
            actualView?.shimmer_home?.stopShimmer()
            actualView?.shimmer_home?.gone()
            actualView?.rvBestSeller?.setUpHorizontal(listAllData, R.layout.item_homefragment, {
                ivHomeCasing.loadUrl(it.photoUrl)
                tvHomeTypeCasing.text = it.casingType
                when (it.isTopSeller) {
                    true -> tvHomeIsBestSeller.visible()
                }
            })
        }
    }

    override fun initView(view: View) {
        this.actualView = view
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