package com.junemon.junemoncase.ui.fragment.orderbucketlist.wishlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.junemon.junemoncase.R
import com.junemon.junemoncase.model.OrderCasingModel
import com.junemon.junemoncase.util.*
import kotlinx.android.synthetic.main.fragment_wishlist.view.*
import kotlinx.android.synthetic.main.item_wishlist.view.*

/**
 *
Created by Ian Damping on 15/05/2019.
Github = https://github.com/iandamping
 */
class WishListFragment : Fragment(), WishListView {
    private lateinit var presenter: WishListPresenter
    private var actualView: View? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = WishListPresenter().apply {
            attachView(this@WishListFragment, this@WishListFragment)
            onAttach()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views = container?.inflates(R.layout.fragment_wishlist)
        views?.let { presenter.onCreateView(it) }
        return views
    }

    override fun onSuccessGetListCaseData(data: List<OrderCasingModel>?) {
        actualView?.shimmer_wishlist?.stopShimmer()
        actualView?.shimmer_wishlist?.gone()
        data?.let { nonNullData ->
            actualView?.rvWishList?.setUpVertical(nonNullData, R.layout.item_wishlist, {
                ivWishList.loadUrl(it.photoUrl)
                tvWishListPhoneType.text = it.phoneType
                tvWishListCaseType.text = it.casingType
            })
        }

    }

    override fun onFailedGetListCaseData(msg: String) {
        logE(msg)
    }

    override fun initView(view: View) {
        this.actualView = view
    }

    override fun onNotLoginYet() {
        context?.alertHelperToLoginActivity(resources.getString(R.string.login_first))
    }

    override fun onPause() {
        super.onPause()
        actualView?.shimmer_wishlist?.stopShimmer()
    }

    override fun onResume() {
        super.onResume()
        actualView?.shimmer_wishlist?.startShimmer()
    }
}