package com.junemon.junemoncase.data.localdata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.junemon.junemoncase.model.UserProfileModel

/**
 *
Created by Ian Damping on 17/05/2019.
Github = https://github.com/iandamping
 */
@Database(entities = [UserProfileModel::class], version = 1, exportSchema = false)
abstract class JunemonDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var Instace: JunemonDatabase? = null

        fun getInstance(context: Context): JunemonDatabase? {
            if (Instace == null) {
                synchronized(JunemonDatabase::class) {
                    Instace = Room.databaseBuilder(
                            context.applicationContext,
                            JunemonDatabase::class.java, "JunemonCaseLocalData"
                    ).build()
                }
            }
            return Instace
        }
    }

    abstract fun userDao(): JunemonDao
}