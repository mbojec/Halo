package com.mbojec.halo.background

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mbojec.halo.HaloApplication
import timber.log.Timber

class ForecastWorker(context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters){
    override fun doWork(): Result {
        return try {
            val list = (applicationContext as HaloApplication).database.forecastDao().loadForecastsList()
            (applicationContext as HaloApplication).networkRepository.updateForecast(list)
            Timber.i("Worker attempt")
            Result.success()
        } catch (exception: Exception){
            Timber.e(exception, "Error loading data")
            Result.failure()
        }
    }

}