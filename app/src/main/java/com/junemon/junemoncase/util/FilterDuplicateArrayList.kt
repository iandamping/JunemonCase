package com.junemon.junemoncase.util

/**
 *
Created by Ian Damping on 28/03/2019.
Github = https://github.com/iandamping
 */

fun <T> MutableList<T>.removeDuplicates(): MutableList<T> {

    // Create a new ArrayList
//    val newList = ArrayList<T>()

    // Traverse through the first list
    for (element in this) {

        // If this element is not present in newList
        // then add it
        if (!this.contains(element)) {
            this.add(element)
        }
    }

    // return the new list
    return this
}
