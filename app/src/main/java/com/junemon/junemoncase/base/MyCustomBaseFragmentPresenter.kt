package com.junemon.junemoncase.base

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.junemon.junemoncase.JunemonApps.Companion.DatabasesAccess
import com.junemon.junemoncase.JunemonApps.Companion.gson
import com.junemon.junemoncase.JunemonApps.Companion.mFirebaseAuth
import com.junemon.junemoncase.JunemonApps.Companion.prefHelper
import com.junemon.junemoncase.JunemonApps.Companion.userDatabaseReference
import com.junemon.junemoncase.R
import com.junemon.junemoncase.model.UserProfileModel
import com.junemon.junemoncase.ui.activity.MainActivity
import com.junemon.junemoncase.util.*
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.layoutInflater

/**
 *
Created by Ian Damping on 16/05/2019.
Github = https://github.com/iandamping
 */
abstract class MyCustomBaseFragmentPresenter<View> : LifecycleObserver, MyCustomBaseFragmentPresenterHelper {
    private lateinit var alert: AlertDialog
    private lateinit var lifecycleOwner: Fragment
    private var listener: FirebaseAuth.AuthStateListener? = null
    private lateinit var userData: UserProfileModel
    private var view: View? = null
    private var viewLifecycle: Lifecycle? = null
    protected var compose: CompositeDisposable = CompositeDisposable()

    fun attachView(view: View, lifeCycleOwner: Fragment) {
        this.view = view
        this.lifecycleOwner = lifeCycleOwner
        setBaseDialog(lifecycleOwner.context)
        lifeCycleOwner.lifecycle.addObserver(this)
    }

    fun onGetUserData(loggedIn: (UserProfileModel) -> Unit, notLoggedIn: () -> Unit) {
        listener = FirebaseAuth.AuthStateListener {
            if (it.currentUser != null) {
                getLifeCycleOwner().customViewModelFactoriesHelper({GenericViewModelWithLiveData(DatabasesAccess?.userDao()?.loadAllLocalUserData())}){
                    with(this){
                        getGenericViewModelData()?.observe(lifecycleOwner.viewLifecycleOwner, Observer { localData ->
                            if (localData!=null){
                                if (localData.userID == it.currentUser!!.uid){
                                    loggedIn(localData)
                                }
                            } else {
                                    this@MyCustomBaseFragmentPresenter.userData = UserProfileModel(
                                        null,
                                        it.currentUser?.uid,
                                        it.currentUser?.photoUrl.toString(),
                                        it.currentUser?.displayName,
                                        it.currentUser?.email,
                                        it.currentUser?.phoneNumber,
                                        null,
                                        null,
                                        null
                                    )
                                    it.currentUser?.uid?.let { currentUserData ->
                                        userDatabaseReference.child(currentUserData).setValue(userData)
                                    }
                                    compose.asyncRxExecutor{
                                        DatabasesAccess?.userDao()?.insertLocalUserData(userData)
                                    }
                                    lifecycleOwner.context?.startActivity<MainActivity>()
                                    loggedIn(userData)
                            }
                        })
                    }
                }
//                if (!prefHelper.getStringInSharedPreference(Constant.saveUserData).isNullOrBlank()) {
//                    this.userData = gson.fromJson(prefHelper.getStringInSharedPreference(Constant.saveUserData), UserProfileModel::class.java)
//                    loggedIn(userData)
//
//                } else if (prefHelper.getStringInSharedPreference(Constant.saveUserData).isNullOrBlank()) {
//                    if (it.currentUser != null) {
//                        this.userData = UserProfileModel(
//                            null,
//                                it.currentUser?.uid,
//                                it.currentUser?.photoUrl.toString(),
//                                it.currentUser?.displayName,
//                                it.currentUser?.email,
//                                it.currentUser?.phoneNumber,
//                                null,
//                                null,
//                                null
//                        )
//                        it.currentUser?.uid?.let { currentUserData ->
//                            userDatabaseReference.child(currentUserData).setValue(userData)
//                        }
//                        compose.asyncRxExecutor{
//                            DatabasesAccess?.userDao()?.insertLocalUserData(userData)
//                        }
//                        lifecycleOwner.context?.startActivity<MainActivity>()
////                        if (prefHelper.getStringInSharedPreference(Constant.saveUserData).isNullOrBlank()) {
////                            prefHelper.saveStringInSharedPreference(Constant.saveUserData, gson.toJson(userData))
////                            lifecycleOwner.context?.startActivity<MainActivity>()
////                        } else if (!prefHelper.getStringInSharedPreference(Constant.saveUserData).isNullOrBlank()) {
////                            lifecycleOwner.context?.startActivity<MainActivity>()
////
////                        }
//                        loggedIn(userData)
//                    }
//                }

            } else notLoggedIn()
        }
    }


    private fun setBaseDialog(ctx: Context?) {
        val dialogBuilder = ctx?.let { AlertDialog.Builder(it) }
        val inflater = ctx?.layoutInflater
        val dialogView = inflater?.inflate(R.layout.custom_loading, null)

        dialogBuilder?.setView(dialogView)
        alert = dialogBuilder?.create()!!
        alert.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alert.setCancelable(false)
        alert.setCanceledOnTouchOutside(false)

    }

    protected fun setUserLogout() {
        lifecycleOwner.context?.let { AuthUI.getInstance().signOut(it) }
        lifecycleOwner.context?.startActivity<MainActivity>()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onViewDestroyed() {
        view = null
        viewLifecycle = null
        if (!compose.isDisposed) compose.dispose()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        if (listener != null) {
            mFirebaseAuth.addAuthStateListener(listener!!)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun onPause() {
        if (listener != null) {
            mFirebaseAuth.removeAuthStateListener(listener!!)
        }
    }

    protected fun getLifeCycleOwner(): Fragment {
        return lifecycleOwner
    }

    protected fun view(): View? {
        return view
    }

    protected fun setDialogShow(status: Boolean) {
        if (!status) {
            alert.show()
        } else {
            alert.dismiss()
        }
    }
}