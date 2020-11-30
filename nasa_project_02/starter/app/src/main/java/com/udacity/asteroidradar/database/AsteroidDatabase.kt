package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.Asteroid


@Database(entities = [Asteroid::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {

    abstract val asteroidDatabaseDao: AsteroidDatabaseDao

    //private lateinit var INSTANCE: AsteroidDatabase


    companion object {
        @Volatile
        private var INSTANCE: AsteroidDatabase? = null

        fun getInstance(context: Context): AsteroidDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                      context.applicationContext,
                      AsteroidDatabase::class.java,
                      "asteroids_table"
                    )
                      .fallbackToDestructiveMigration()
                      .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
/*
    fun getInstance(context: Context): AsteroidDatabase {
        synchronized(AsteroidDatabase::class.java) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                  context.applicationContext,
                  AsteroidDatabase::class.java,
                  "asteroids_table"
                ).build()
            }
        }
        return INSTANCE
    }
}
*/