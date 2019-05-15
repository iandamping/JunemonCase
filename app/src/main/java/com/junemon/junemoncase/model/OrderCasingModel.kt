package com.junemon.junemoncase.model

/**
 *
Created by Ian Damping on 24/04/2019.
Github = https://github.com/iandamping
 */
class OrderCasingModel(
    var nameUser: String?, var phoneNumberUser: String?, var addressUser: String?,
    var provinceUser: String?, var cityUser: String?, var casingType: String?,
    var photoUrl: String?,var donePayment:Boolean?){
    constructor():this(null,null,null,null,null,null,null,null)
}