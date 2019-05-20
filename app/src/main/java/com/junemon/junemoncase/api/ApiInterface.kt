package com.junemon.junemoncase.api

import com.junemon.junemoncase.model.GeneralProvinceModel
import com.junemon.junemoncase.model.GeneralResponseModel
import io.reactivex.Single
import retrofit2.http.GET

/**
 *
Created by Ian Damping on 26/04/2019.
Github = https://github.com/iandamping
 */
interface ApiInterface {
    @GET(NetworkConfig.GET_PROVINCE)
    fun getAllProvinceData(): Single<GeneralResponseModel<GeneralProvinceModel>>

    @GET(NetworkConfig.GET_CITY)
    fun getAllCityData(): Single<GeneralResponseModel<GeneralProvinceModel>>
}