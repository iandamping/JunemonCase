package com.junemon.junemoncase.util

import androidx.fragment.app.Fragment
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.junemon.junemoncase.data.HomeViewModel
import com.junemon.junemoncase.model.AllCasingModel

/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */

fun Fragment.getAllDataFromFirebase(data: DatabaseReference) {
    val vm = this.viewModelHelperForFragment<HomeViewModel>()
    data.addChildEventListener(object : ChildEventListener {
        override fun onCancelled(p0: DatabaseError) {
        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            vm.setCasingData(p0.getValue(AllCasingModel::class.java)!!)
        }

        override fun onChildRemoved(p0: DataSnapshot) {
        }

    })
}
