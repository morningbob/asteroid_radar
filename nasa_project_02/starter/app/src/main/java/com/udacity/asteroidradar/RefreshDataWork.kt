package com.udacity.asteroidradar

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.AsteroidRepository
import retrofit2.HttpException
/*
class RefreshDataWork(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val database =
            //applicationContext?.let { AsteroidDatabase.getInstance(it).asteroidDatabaseDao }
            AsteroidDatabase.getInstance(applicationContext)

        val repository = AsteroidRepository(database)
        return try {
            repository.refreshAsteroidList()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }

    }

    }
    */
