package com.junemon.junemoncase.ui.fragment.home

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.firebase.database.DatabaseReference
import com.junemon.junemoncase.R
import com.junemon.junemoncase.base.BaseFragmentPresenter
import com.junemon.junemoncase.data.HomeViewModel
import com.junemon.junemoncase.util.getAllDataFromFirebase
import com.junemon.junemoncase.util.withViewModel

/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */
class HomePresenter(private val dataReference: DatabaseReference, var mView: HomeView, var target: Fragment) :
        BaseFragmentPresenter() {
    private var ctx: Context? = null

    override fun onAttach(context: Context?) {
        this.ctx = context
        target.getAllDataFromFirebase(dataReference)
    }

    override fun onCreateView(view: View) {
        mView.initView(view)
    }


    fun onGetData() {
        target.withViewModel<HomeViewModel> {
            this.getCasingData()?.observe(target.viewLifecycleOwner, Observer {
                when (it) {
                    null -> mView.onFailGetData(ctx?.getString(R.string.error_happen))
                    else -> mView.onSuccesGetData(it)
                }

            })
        }
    }
}