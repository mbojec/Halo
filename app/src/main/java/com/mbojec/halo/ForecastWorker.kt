package com.mbojec.halo

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import timber.log.Timber

class ForecastWorker(context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters){
    override fun doWork(): Result {
        return try {
            val list = (applicationContext as HaloApplication).database.forecastDao().loadForecastsList()
            (applicationContext as HaloApplication).networkRepository.updateForecast(list)
            (applicationContext as HaloApplication).sharedPreferencesUtils.saveDataUpdateTime()
            Timber.i("Worker attempt")
            Result.success()
        } catch (exception: Exception){
            Timber.e(exception, "Error loading data")
            Result.failure()
        }
    }

}