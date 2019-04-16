package com.junemon.junemoncase.model

/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */
data class AllCasingModel(
    var casingType: String?,
    var isTopSeller: Boolean?,
    var photoUrl: String?,
    var casingPhotoLastPathSegment: String?
) {
    constructor() : this(null, null, null, null)
}