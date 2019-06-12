package com.junemon.junemoncase.base

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import com.google.firebase.auth.FirebaseAuth
import com.ian.app.helper.model.GenericViewModelWithLiveData
import com.ian.app.helper.util.layoutInflater
import com.junemon.junemoncase.JunemonApps
import com.junemon.junemoncase.JunemonApps.Companion.mFirebaseAuth
import com.junemon.junemoncase.R
import com.junemon.junemoncase.model.UserProfileModel
import com.junemon.junemoncase.util.customViewModelFactoriesHelper
import io.reactivex.disposables.CompositeDisposable

/**
 *
Created by Ian Damping on 16/05/2019.
Github = https://github.com/iandamping
 */
abstract class MyCustomBasePresenter<View> : LifecycleObserver, MyCustomBasePresenterHelper {
    private var listener: FirebaseAuth.AuthStateListener? = null
    private lateinit var alert: AlertDialog
    protected val compose: CompositeDisposable = CompositeDisposable()
    private lateinit var lifecycleOwner: FragmentActivity
    private var view: View? = null

    fun attachView(view: View, lifeCycleOwner: FragmentActivity) {
        this.view = view
        this.lifecycleOwner = lifeCycleOwner
        setBaseDialog(lifecycleOwner)
        lifeCycleOwner.lifecycle.addObserver(this)
    }

    protected fun onGetUserData(loggedIn: (UserProfileModel) -> Unit, notLoggedIn: () -> Unit) {
        listener = FirebaseAuth.AuthStateListener {
            if (it.currentUser != null) {
                lifecycleOwner.customViewModelFactoriesHelper({ GenericViewModelWithLiveData(JunemonApps.DatabasesAccess?.userDao()?.loadAllLocalUserData()) }) {
                    with(this) {
                        getGenericViewModelData()?.observe(lifecycleOwner, Observer { localData ->
                            if (localData != null) {
                                localData.forEach { singleData ->
                                    if (singleData.userID == it.currentUser!!.uid) {
                                        loggedIn(singleData)
                                    }
                                }

                            } else {
                                notLoggedIn()
                            }
                        })
                    }
                }
            } else notLoggedIn()
        }
    }

    private fun setBaseDialog(ctx: Context) {
        val dialogBuilder = AlertDialog.Builder(ctx)
        val inflater = ctx.layoutInflater
        val dialogView = inflater.inflate(R.layout.custom_loading, null)

        dialogBuilder.setView(dialogView)
        alert = dialogBuilder.create()
        alert.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alert.setCancelable(false)
        alert.setCanceledOnTouchOutside(false)

    }

    protected fun setDialogShow(status: Boolean) {
        if (!status) {
            alert.show()
        } else {
            alert.dismiss()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onViewDestroyed() {
        view = null
        if (!compose.isDisposed) compose.dispose()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        listener?.let { nonNullListener -> mFirebaseAuth.addAuthStateListener(nonNullListener) }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun onPause() {
        listener?.let { nonNullListener -> mFirebaseAuth.removeAuthStateListener(nonNullListener) }
    }

    protected fun getLifeCycleOwner(): FragmentActivity {
        return lifecycleOwner
    }

    protected fun view(): View? {
        return view
    }

}