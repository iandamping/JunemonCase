package com.junemon.junemoncase.model

import com.google.gson.annotations.SerializedName

/**
 *
Created by Ian Damping on 26/04/2019.
Github = https://github.com/iandamping
 */
data class GeneralProvinceModel(
    @field:SerializedName("city_id") var cityId: String?,
    @field:SerializedName("province_id") var provinceId: String?,
    @field:SerializedName("province") var provinceName: String?,
    @field:SerializedName("type") var type: String?,
    @field:SerializedName("city_name") var cityName: String?,
    @field:SerializedName("postal_code") var postalCode: String?
)