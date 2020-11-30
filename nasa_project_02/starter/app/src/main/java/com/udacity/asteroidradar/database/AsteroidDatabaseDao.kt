package com.udacity.asteroidradar.database

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg asteroids: Asteroid?)

    //@Query("SELECT * FROM asteroids_table ORDER BY closeApproachDate")
    //fun getAllAsteroids(): MutableLiveData<List<Asteroid>>
}