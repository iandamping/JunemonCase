package com.junemon.junemoncase

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import com.junemon.junemoncase.api.ApiInterface
import com.junemon.junemoncase.api.NetworkConfig
import com.junemon.junemoncase.data.JunemonDatabase
import com.junemon.junemoncase.util.Constant.nodeOrders
import com.junemon.junemoncase.util.Constant.nodePhoneType
import com.junemon.junemoncase.util.Constant.nodePhotos
import com.junemon.junemoncase.util.Constant.nodeUsers
import com.junemon.junemoncase.util.PreferenceHelper
import io.fabric.sdk.android.Fabric

/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */
class JunemonApps : Application() {
    companion object {
        lateinit var prefHelper: PreferenceHelper
        lateinit var gson: Gson
        lateinit var userDatabaseReference: DatabaseReference
        lateinit var phoneTypeDatabaseReference: DatabaseReference
        lateinit var mAllImageDatabaseReference: DatabaseReference
        lateinit var mAllOrderDatabaseReference: DatabaseReference
        lateinit var storageDatabaseReference: StorageReference
        lateinit var mFirebaseAuth: FirebaseAuth
        lateinit var mFirebaseDatabase: FirebaseDatabase
        lateinit var mFirebaseStorage: FirebaseStorage
        lateinit var api: ApiInterface
        var DatabasesAccess: JunemonDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
        prefHelper = PreferenceHelper(this)
        gson = Gson()
        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseStorage = FirebaseStorage.getInstance()
        mAllImageDatabaseReference = mFirebaseDatabase.reference.child(nodePhotos)
        mAllOrderDatabaseReference = mFirebaseDatabase.reference.child(nodeOrders)
        userDatabaseReference = mFirebaseDatabase.reference.child(nodeUsers)
        phoneTypeDatabaseReference = mFirebaseDatabase.reference.child(nodePhoneType)
        storageDatabaseReference = mFirebaseStorage.getReferenceFromUrl("gs://junemon-case.appspot.com/")
        api = NetworkConfig.getRetrofit().create(ApiInterface::class.java)
        DatabasesAccess = JunemonDatabase.getInstance(applicationContext)
    }
}