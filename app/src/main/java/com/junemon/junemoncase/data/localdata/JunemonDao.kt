package com.junemon.junemoncase.data.localdata

import androidx.lifecycle.LiveData
import androidx.room.*
import com.junemon.junemoncase.model.UserProfileModel

/**
 *
Created by Ian Damping on 17/05/2019.
Github = https://github.com/iandamping
 */
@Dao
interface JunemonDao {
    @Query("SELECT * FROM user_data ")
    fun loadAllLocalUserData(): LiveData<UserProfileModel>

    @Query("SELECT * FROM user_data WHERE local_user_id = :id")
    fun loadAllLocalUserDataById(id: Int?): LiveData<UserProfileModel>

    @Insert
    fun insertLocalUserData(inputUser: UserProfileModel?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateLocalUserData(updateUser: UserProfileModel)

    @Query("DELETE FROM user_data")
    fun deleteAllLocalUserData()
}