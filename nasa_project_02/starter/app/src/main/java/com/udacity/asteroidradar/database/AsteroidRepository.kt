package com.udacity.asteroidradar.database

import com.udacity.asteroidradar.Network
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception

class AsteroidRepository(private val database: AsteroidDatabase) {

    //GlobalScope.launch {
    //    refreshAsteroidList()
    //}
    //Log.i("asteroidList size: ", _asteroidList.value?.size.toString())

    //private val _asteroidList: MutableLiveData<List<Asteroid>>()

    suspend fun refreshAsteroidList() {
        //var asteroidList: List<Asteroid>
        withContext(Dispatchers.IO) {

            try {
                val responseString = Network.apiService
                    .getAsteroids(startDate = "2022-11-26", endDate = "2022-12-03").await()

                val jsonAsteroidList = JSONObject(responseString)
                val asteroidList = parseAsteroidsJsonResult(jsonAsteroidList)
                val databaseDao = database.asteroidDatabaseDao
                    //context?.let { AsteroidDatabase.getInstance(it).asteroidDatabaseDao }
                //databaseDao.insertAll(*asteroidList.toTypedArray())
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

}