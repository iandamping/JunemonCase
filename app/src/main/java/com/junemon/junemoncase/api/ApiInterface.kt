package com.junemon.junemoncase.api

import com.junemon.junemoncase.model.GeneralProvinceModel
import com.junemon.junemoncase.model.GeneralResponseModel
import retrofit2.Call
import retrofit2.http.GET

/**
 *
Created by Ian Damping on 26/04/2019.
Github = https://github.com/iandamping
 */
interface ApiInterface {
    @GET(NetworkConfig.GET_PROVINCE)
    fun getAllProvinceData(): Call<GeneralResponseModel<GeneralProvinceModel>>

    @GET(NetworkConfig.GET_CITY)
    fun getAllCityData(): Call<GeneralResponseModel<GeneralProvinceModel>>
}