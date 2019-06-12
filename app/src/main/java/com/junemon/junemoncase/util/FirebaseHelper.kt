package com.junemon.junemoncase.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.ian.app.helper.model.GenericViewModel

/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */

inline fun <reified T> Fragment.getAllDataFromFirebase(data: DatabaseReference) {
    val vm = this.viewModelHelperForFragment<GenericViewModel<T>>()
    data.addChildEventListener(object : ChildEventListener {
        override fun onCancelled(p0: DatabaseError) {
        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            if (p0.getValue(T::class.java) != null) {
                vm.setGenericData(p0.getValue(T::class.java)!!)
            }
        }

        override fun onChildRemoved(p0: DataSnapshot) {
        }

    })
}

inline fun <reified T> FragmentActivity.getAllDataFromFirebase(data: DatabaseReference) {
    val vm = this.viewModelHelperForActivity<GenericViewModel<T>>()
    data.addChildEventListener(object : ChildEventListener {
        override fun onCancelled(p0: DatabaseError) {
        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            if (p0.getValue(T::class.java) != null) {
                vm.setGenericData(p0.getValue(T::class.java)!!)
            }
        }

        override fun onChildRemoved(p0: DataSnapshot) {
        }

    })
}
