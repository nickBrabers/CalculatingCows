package com.example.calculatingcows.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Cow::class], version = 1, exportSchema = false)
abstract class CowDatabase : RoomDatabase(){

    abstract val cowDatabaseDao: CowDatabaseDao

    companion object {

        @Volatile
        private var INSTACE : CowDatabase? = null

        fun getInstance(context: Context):CowDatabase {

            synchronized(this) {
                var instance = INSTACE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, CowDatabase::class.java, "cow_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTACE = instance
                }
                return instance
            }
        }
    }
}