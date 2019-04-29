package com.junemon.junemoncase.model

/**
 *
Created by Ian Damping on 22/04/2019.
Github = https://github.com/iandamping
 */
data class PhoneTypeModel(var listPhoneTypedata: MutableMap<String, PhoneType>?) {

    data class PhoneType(var listCasingType: List<String>?) {
        constructor() : this(null)
    }

    constructor() : this(null)
}