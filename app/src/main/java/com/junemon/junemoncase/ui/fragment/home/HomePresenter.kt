package com.junemon.junemoncase.ui.fragment.home

import android.content.Context
import android.view.View
import androidx.lifecycle.Observer
import com.google.firebase.database.DatabaseReference
import com.ian.app.helper.model.GenericViewModel
import com.junemon.junemoncase.base.MyCustomBaseFragmentPresenter
import com.junemon.junemoncase.model.AllCasingModel
import com.junemon.junemoncase.util.getAllDataFromFirebase
import com.junemon.junemoncase.util.withViewModel

/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */
class HomePresenter(private val dataReference: DatabaseReference) : MyCustomBaseFragmentPresenter<HomeView>() {
    private var ctx: Context? = null
    private var listAllData: MutableList<AllCasingModel> = mutableListOf()
    private var listHardcase: MutableList<AllCasingModel> = mutableListOf()
    private var listSoftcase: MutableList<AllCasingModel> = mutableListOf()
    private var listPremium: MutableList<AllCasingModel> = mutableListOf()
    private var listPremiumSoft: MutableList<AllCasingModel> = mutableListOf()
    private var listAirBag: MutableList<AllCasingModel> = mutableListOf()
    private var listBestSeller: MutableList<AllCasingModel> = mutableListOf()

    override fun onAttach() {
        this.ctx = getLifeCycleOwner().context
        getLifeCycleOwner().getAllDataFromFirebase<AllCasingModel>(dataReference)
    }

    override fun onCreateView(view: View) {
        view()?.initView(view)
    }


    fun onGetData() {
        getLifeCycleOwner().withViewModel<GenericViewModel<AllCasingModel>> {
            this.getGenericData().observe(getLifeCycleOwner().viewLifecycleOwner, Observer {
                listAllData.clear()
                listAllData.add(it)
                listAllData.forEach { customData ->
                    if (customData.isTopSeller != null) {
                        when {
                            customData.casingType.equals("Hardcase") && !customData.isTopSeller!! -> {
                                listHardcase.add(customData)
                                view()?.onSuccesGetHardcaseData(listHardcase)
                            }
                            customData.casingType.equals("Softcase") && !customData.isTopSeller!! -> {
                                listSoftcase.add(customData)
                                view()?.onSuccesGetSoftcaseData(listSoftcase)
                            }
                            customData.casingType.equals("Premium") && !customData.isTopSeller!! -> {
                                listPremium.add(customData)
                                view()?.onSuccesGetPremiumData(listPremium)
                            }
                            customData.casingType.equals("Premium Soft") && !customData.isTopSeller!! -> {
                                listPremiumSoft.add(customData)
                                view()?.onSuccesGetPremiumSoftData(listPremiumSoft)
                            }
                            customData.casingType.equals("Air Bag") && !customData.isTopSeller!! -> {
                                listAirBag.add(customData)
                                view()?.onSuccesGetAirBagData(listAirBag)
                            }
                            customData.isTopSeller!! -> {
                                listBestSeller.add(customData)
                                view()?.onSuccesGetBestSellerData(listBestSeller)
                            }
                        }
                    }

                }
            })
        }
    }
}