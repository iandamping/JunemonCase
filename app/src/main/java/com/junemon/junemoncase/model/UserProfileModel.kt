package com.junemon.junemoncase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
Created by Ian Damping on 19/04/2019.
Github = https://github.com/iandamping
 */
@Entity(tableName = "user_data")
data class UserProfileModel(@PrimaryKey(autoGenerate = true) var local_user_id: Int?,
                            @ColumnInfo(name = "firebase_user_id") var userID: String?,
                            @ColumnInfo(name = "firebase_photo_user") var photoUser: String?,
                            @ColumnInfo(name = "firebase_name_user") var nameUser: String?,
                            @ColumnInfo(name = "firebase_email_user") var emailUser: String?,
                            @ColumnInfo(name = "firebase_phone_number_user") var phoneNumberUser: String?,
                            @ColumnInfo(name = "firebase_address_user") var addressUser: String?,
                            @ColumnInfo(name = "firebase_province_user") var provinceUser: String?,
                            @ColumnInfo(name = "firebase_city_user") var cityUser: String?
) {
    constructor() : this(null, null, null, null, null, null, null, null, null)
}