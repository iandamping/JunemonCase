package com.junemon.junemoncase.ui.fragment.orderbucketlist.wishlist

import android.content.Context
import android.view.View
import androidx.lifecycle.Observer
import com.junemon.junemoncase.JunemonApps.Companion.mAllOrderDatabaseReference
import com.junemon.junemoncase.base.MyCustomBaseFragmentPresenter
import com.junemon.junemoncase.data.GenericViewModel
import com.junemon.junemoncase.model.OrderCasingModel
import com.junemon.junemoncase.util.getAllDataFromFirebase
import com.junemon.junemoncase.util.withViewModel

/**
 *
Created by Ian Damping on 16/05/2019.
Github = https://github.com/iandamping
 */
class WishListPresenter : MyCustomBaseFragmentPresenter<WishListView>() {
    private var ctx: Context? = null
    private var listAllData: MutableList<OrderCasingModel> = mutableListOf()
    private var listSpesificUser: MutableList<OrderCasingModel> = mutableListOf()

    override fun onAttach() {
        this.ctx = getLifeCycleOwner().context
    }

    override fun onCreateView(view: View) {
        onGetUserData({
            getLifeCycleOwner().getAllDataFromFirebase<OrderCasingModel>(mAllOrderDatabaseReference)
            it.userID?.let { nonNull ->
                getLifeCycleOwner().withViewModel<GenericViewModel<OrderCasingModel>> {
                    this.getGenericData().observe(getLifeCycleOwner().viewLifecycleOwner, Observer { modelData ->
                        listAllData.clear()
                        listAllData.add(modelData)
                        listAllData.forEach { listDataForEachings ->
                            if (nonNull.equals(listDataForEachings.userID, ignoreCase = true)) {
                                listSpesificUser.add(listDataForEachings)
                                view()?.onSuccessGetListCaseData(listSpesificUser)
                            } else view()?.onNoWishlistCase()
                        }
                    })
                }
            }
        }) {
            view()?.onNotLoginYet()
        }

        view()?.initView(view)
    }

}