package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.*

import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.AsteroidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel(private val database: AsteroidDatabase) : ViewModel() {

    private val asteroidRepository = AsteroidRepository(database)

    var _asteroidList = MutableLiveData<List<Asteroid?>?>()

    private val _picture = MutableLiveData<PictureOfDay?>()

    val picture: LiveData<PictureOfDay?>
        get() = _picture

    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()

    val asteroidList: LiveData<List<Asteroid?>?>
        get() = _asteroidList

    var unfilteredAsteroidList: List<Asteroid?> = emptyList()

    val navigateToSelectedAsteroid: LiveData<Asteroid>
        get() = _navigateToSelectedAsteroid

    init {
        refreshAsteroids()
        updatePicture()
    }

    fun refreshAsteroids() {

        viewModelScope.launch {
            asteroidRepository.refreshAsteroidList()
            _asteroidList.value = asteroidRepository.getAsteroids()
        }
    }

    fun updatePicture() {

        viewModelScope.launch {
            asteroidRepository.updatePicture()
            _picture.value = asteroidRepository.getPicture()
        }
    }

    fun onWeekClicked() {
        val days = getNextSevenDaysFormattedDates()
        unfilteredAsteroidList = _asteroidList.value!!
        var filteredResult: ArrayList<Asteroid> = emptyList<Asteroid>() as ArrayList<Asteroid>

        viewModelScope.launch {
            _asteroidList.value = asteroidRepository.getAsteroids()
        }
        for (asteroid in unfilteredAsteroidList) {
            if (asteroid?.closeApproachDate in days)
                asteroid?.let { filteredResult.add(it) }
        }
        _asteroidList.value = filteredResult
    }

    fun onTodayClicked() {
        unfilteredAsteroidList = _asteroidList.value!!
        var filteredResult: ArrayList<Asteroid> = emptyList<Asteroid>() as ArrayList<Asteroid>
        val startDate = asteroidRepository.getStartDate()
        for (asteroid in unfilteredAsteroidList) {
            if (asteroid?.closeApproachDate == startDate)
                asteroid?.let { filteredResult.add(it) }
        }
        _asteroidList.value = filteredResult
    }

    fun onSavedClicked() {
        var asteroids : List<Asteroid?>? = null
        viewModelScope.launch {
            asteroids = asteroidRepository.getAsteroids()
        }
        _asteroidList.value = asteroids
    }

    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToSelectedAsteroid.value = asteroid
    }

    fun displayAsteroidComplete() {
        _navigateToSelectedAsteroid.value = null
    }
/*
    fun getAsteroids() {
        val asteroidRepository = AsteroidRepository(database)
        viewModelScope.launch {
            _asteroidList.value = asteroidRepository.getAsteroids()
        }
    }

    fun getPicture() {
        val asteroidRepository = AsteroidRepository(database)
        viewModelScope.launch {
            _picture.value = asteroidRepository.getPicture()
        }
    }

    fun onFilterSelect(filter: AsteroidRepository.Query) {
        val asteroidRepository = AsteroidRepository(database)
        asteroidRepository.applyFilter(filter)
    }
 */
}

class MainViewModelFactory(val database: AsteroidDatabase)

     : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}