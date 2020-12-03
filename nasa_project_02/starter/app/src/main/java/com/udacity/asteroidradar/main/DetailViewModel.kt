package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidApplication
/*
class DetailViewModel(val asteroidDetails: Asteroid, application: Application)
    : ViewModel() {

    private val _asteroid = MutableLiveData<Asteroid>()

    var asteroid: LiveData<Asteroid?>
        get() = _asteroid

    init {
        _asteroid.value = asteroidDetails
    }

}

class DetailViewModelFactory (
    private val asteroid: Asteroid,
    private val application: Application
    //private val databaseDao: AsteroidDatabaseDao
    //private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(asteroid, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
*/
