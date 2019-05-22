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
import com.junemon.junemoncase.JunemonApps.Companion.mFirebaseAuth
import com.junemon.junemoncase.JunemonApps.Companion.userDatabaseReference
import com.junemon.junemoncase.R
import com.junemon.junemoncase.data.GenericViewModel
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
    private var userData: UserProfileModel = UserProfileModel()
    private var view: View? = null
    protected var compose: CompositeDisposable = CompositeDisposable()

   fun attachView(view: View, lifeCycleOwner: Fragment) {
        this.view = view
        this.lifecycleOwner = lifeCycleOwner
        setBaseDialog(lifecycleOwner.context)
        lifeCycleOwner.lifecycle.addObserver(this)
    }

    protected fun onGetUserData(loggedIn: (UserProfileModel) -> Unit, notLoggedIn: () -> Unit) {
        listener = FirebaseAuth.AuthStateListener { auth ->
            if (auth.currentUser != null) {
                with(this@MyCustomBaseFragmentPresenter.userData) {
                    userID = auth.currentUser?.uid
                    photoUser = auth.currentUser?.photoUrl.toString()
                    nameUser = auth.currentUser?.displayName
                    emailUser = auth.currentUser?.email
                    phoneNumberUser = auth.currentUser?.phoneNumber
                }
                //Room  && Firebase way
                lifecycleOwner.customViewModelFactoriesHelper({ GenericViewModelWithLiveData(DatabasesAccess?.userDao()?.loadAllLocalUserData()) }) {
                    getGenericViewModelData()?.observe(lifecycleOwner.viewLifecycleOwner, Observer { localData ->
                        if (localData.isNotEmpty()) {
                            localData.forEach { singleData ->
                                if (singleData.userID == userData.userID) {
                                    loggedIn(singleData)
                                } else if (singleData.userID != userData.userID) {
                                    lifecycleOwner.getAllDataFromFirebase<UserProfileModel>(userDatabaseReference)
                                    lifecycleOwner.withViewModel<GenericViewModel<UserProfileModel>> {
                                        getGenericData().observe(lifecycleOwner.viewLifecycleOwner, Observer { firebaseData ->
                                                if (firebaseData.userID == userData.userID) {
                                                    if (firebaseData !=null){
                                                        with(this@MyCustomBaseFragmentPresenter.userData) {
                                                            local_user_id = 1
                                                            userID = firebaseData.userID
                                                            photoUser = firebaseData.photoUser
                                                            nameUser = firebaseData.nameUser
                                                            emailUser = firebaseData.emailUser
                                                            phoneNumberUser = firebaseData.phoneNumberUser
                                                            addressUser = firebaseData.addressUser
                                                            provinceUser = firebaseData.provinceUser
                                                            cityUser = firebaseData.cityUser
                                                        }
                                                    }
                                                    compose.asyncRxExecutor { DatabasesAccess?.userDao()?.updateLocalUserData(userData) }
                                                    lifecycleOwner.context?.startActivity<MainActivity>()
                                                } else {
                                                    userData.local_user_id = 1
                                                    compose.asyncRxExecutor { DatabasesAccess?.userDao()?.updateLocalUserData(userData) }
                                                    userData.userID?.let { it1 -> userDatabaseReference.child(it1).setValue(userData) }
                                                    lifecycleOwner.context?.startActivity<MainActivity>()
                                                }

                                            })
                                    }
                                }
                            }
                        }
                        if (localData.isEmpty()) {
                            compose.asyncRxExecutor { DatabasesAccess?.userDao()?.insertLocalUserData(userData) }
                            userData.userID?.let { it1 -> userDatabaseReference.child(it1).setValue(userData) }
                            lifecycleOwner.context?.startActivity<MainActivity>()

                        }
                    })
                }

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
        if (!compose.isDisposed) compose.dispose()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        listener?.let { mFirebaseAuth.addAuthStateListener(it) }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onPause() {
        listener?.let { mFirebaseAuth.removeAuthStateListener(it) }
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


//Firebase Way
//                getLifeCycleOwner().getAllDataFromFirebase<UserProfileModel>(userDatabaseReference)
//                getLifeCycleOwner().withViewModel<GenericViewModel<UserProfileModel>> {
//                    getGenericData().observe(getLifeCycleOwner().viewLifecycleOwner, Observer { firebaseData ->
//                        if (firebaseData!=null){
//                            if (firebaseData.userID == it.currentUser!!.uid) {
//                                loggedIn(firebaseData)
//                            }
//                            else {
//                                this@MyCustomBaseFragmentPresenter.userData = UserProfileModel(
//                                        null,
//                                        it.currentUser?.uid,
//                                        it.currentUser?.photoUrl.toString(),
//                                        it.currentUser?.displayName,
//                                        it.currentUser?.email,
//                                        it.currentUser?.phoneNumber,
//                                        null,
//                                        null,
//                                        null
//                                )
//                                userDatabaseReference.push().setValue(userData)
//                                lifecycleOwner.context?.startActivity<MainActivity>()
//                                loggedIn(userData)
//                            }
//                        }
//                    })
//                }


//Shared Pref way
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
