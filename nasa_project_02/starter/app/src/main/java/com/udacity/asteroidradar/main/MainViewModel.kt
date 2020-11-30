package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidApplication
import com.udacity.asteroidradar.Network
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception

class MainViewModel(application: Application) : ViewModel() {

    private val _asteroidList = MutableLiveData<List<Asteroid>>()

    private val _response = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _response

    val asteroidList: LiveData<List<Asteroid>>
        get() = _asteroidList

    init {

    }







}

class MainViewModelFactory(val application: Application)
    //private val databaseDao: AsteroidDatabaseDao
    //private val application: Application
     : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}