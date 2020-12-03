package com.udacity.asteroidradar.database

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidApplication
import com.udacity.asteroidradar.Constants

import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.main.MainFragment
import com.udacity.asteroidradar.main.MainViewModel
import com.udacity.asteroidradar.main.MainViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.internal.SynchronizedObject
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.TimeZone.getTimeZone


class AsteroidRepository(private val database: AsteroidDatabase) {

    suspend fun refreshAsteroidList() {

        withContext(Dispatchers.IO) {

            try {
                val responseString = Network.apiService
                    .getAsteroids(startDate = getStartDate(), endDate = getEndDate()).await()
                //Log.i("Response ", responseString)

                val jsonAsteroidList = JSONObject(responseString)
                val asteroidList = parseAsteroidsJsonResult(jsonAsteroidList)
                val databaseDao = database.asteroidDatabaseDao
                //Log.i("Asteroid List", asteroidList.size.toString())
                databaseDao.insertAll(*asteroidList.toTypedArray())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getAsteroids() : List<Asteroid?>? {
        var asteroidList = emptyList<Asteroid>()

        withContext(Dispatchers.IO) {
            //runBlocking {
                try {
                    asteroidList = database.asteroidDatabaseDao.getAllAsteroids().value!!
                } catch (e: Exception) {
                    e.printStackTrace()
             //   }
            }
        }
        return asteroidList
    }

    suspend fun updatePicture() {
        withContext(Dispatchers.IO) {
            //runBlocking {
                try {
                    val picture = Network.apiService
                      .getPicture(apiKey = Constants.API_KEY).await()
                    Log.i("Response ", picture.toString())
                    val databaseDao = database.asteroidDatabaseDao
                    databaseDao.insertPicture(picture)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            //}
        }
    }

    suspend fun getPicture(): PictureOfDay? {
        var picture: PictureOfDay? = null
        withContext(Dispatchers.IO) {
            //runBlocking {
                try {
                    picture = database.asteroidDatabaseDao.getPicture().value!!
                } catch (e: Exception) {
                    e.printStackTrace()
             //   }
            }
        }
        return picture
    }

    fun getStartDate(): String {
        val adjustedTimeZone: DateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT)
        adjustedTimeZone.timeZone = getTimeZone("US/Eastern")
        val startDate = adjustedTimeZone.format(System.currentTimeMillis())
        Log.i("Time zone ", startDate)
        return startDate
    }

    fun getEndDate(): String {
        val adjustedTimeZone: DateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT)
        adjustedTimeZone.timeZone = getTimeZone("US/Eastern")
        val endDate = adjustedTimeZone.format(System.currentTimeMillis() +
            7 * 24 * 60 * 60 * 1000)
        return endDate
    }
}