package com.udacity.asteroidradar

import android.app.Application
import android.util.Log
import androidx.work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidApplication : Application() {

    val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        //Log.i("AsteroidApp", " I ran!!!")
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            startWork()
        }

    }

    private fun startWork() {
        val constraints = Constraints.Builder()
          .setRequiredNetworkType(NetworkType.CONNECTED)
          .setRequiresCharging(true).build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWork>(1, TimeUnit.DAYS)
          .setConstraints(constraints)
          .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
          "AsteroidWork",
          ExistingPeriodicWorkPolicy.KEEP,
          repeatingRequest)
    }

}