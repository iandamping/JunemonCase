package com.junemon.junemoncase.ui.fragment.home

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.firebase.database.DatabaseReference
import com.junemon.junemoncase.base.BaseFragmentPresenter
import com.junemon.junemoncase.data.GenericViewModel
import com.junemon.junemoncase.model.AllCasingModel
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
    private var listAllData: MutableList<AllCasingModel> = mutableListOf()
    private var listHardcase: MutableList<AllCasingModel> = mutableListOf()
    private var listSoftcase: MutableList<AllCasingModel> = mutableListOf()
    private var listPremium: MutableList<AllCasingModel> = mutableListOf()
    private var listPremiumSoft: MutableList<AllCasingModel> = mutableListOf()
    private var listAirBag: MutableList<AllCasingModel> = mutableListOf()
    private var listBestSeller: MutableList<AllCasingModel> = mutableListOf()
    override fun onAttach(context: Context?) {
        this.ctx = context
        target.getAllDataFromFirebase<AllCasingModel>(dataReference)
    }

    override fun onCreateView(view: View) {
        mView.initView(view)
    }


    fun onGetData() {
        target.withViewModel<GenericViewModel<AllCasingModel>> {
            this.getGenericData().observe(target.viewLifecycleOwner, Observer {
                listAllData.clear()
                listAllData.add(it)
                listAllData.forEach { customData ->
                    when {
                        customData.casingType.equals("Hardcase") && !customData.isTopSeller!! -> {
                            listHardcase.add(customData)
                            mView.onSuccesGetHardcaseData(listHardcase)
                        }
                        customData.casingType.equals("Softcase") && !customData.isTopSeller!! -> {
                            listSoftcase.add(customData)
                            mView.onSuccesGetSoftcaseData(listSoftcase)
                        }
                        customData.casingType.equals("Premium") && !customData.isTopSeller!! -> {
                            listPremium.add(customData)
                            mView.onSuccesGetPremiumData(listPremium)
                        }
                        customData.casingType.equals("Premium Soft") && !customData.isTopSeller!! -> {
                            listPremiumSoft.add(customData)
                            mView.onSuccesGetPremiumSoftData(listPremiumSoft)
                        }
                        customData.casingType.equals("Air Bag") && !customData.isTopSeller!! -> {
                            listAirBag.add(customData)
                            mView.onSuccesGetAirBagData(listAirBag)
                        }
                        customData.isTopSeller!! -> {
                            listBestSeller.add(customData)
                            mView.onSuccesGetBestSellerData(listBestSeller)
                        }
                    }
                }
            })
        }
    }
}