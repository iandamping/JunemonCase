package com.junemon.junemoncase.model

import com.google.gson.annotations.SerializedName

/**
 *
Created by Ian Damping on 26/04/2019.
Github = https://github.com/iandamping
 */
data class GeneralModel<P>(
        @field:SerializedName("results") var results: List<P>?
)
