package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay

@Dao
interface AsteroidDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: Asteroid?)

    @Query("SELECT * FROM asteroidsTable WHERE strftime('%Y-%m-%d',closeApproachDate)" +
      " >= strftime('%Y-%m-%d','now') ORDER BY date(closeApproachDate) ASC" )
    fun getAllAsteroids(): LiveData<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPicture(picture: PictureOfDay)

    @Query("SELECT * FROM pictureTable ORDER BY ID DESC LIMIT 1")
    fun getPicture(): LiveData<PictureOfDay>

}