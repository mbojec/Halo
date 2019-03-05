package com.mbojec.halo

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.mbojec.halo.utils.DataUtils

object WorkerManager {
    fun startSyncDataDownload(application: HaloApplication, dataUpdateTimeLimit: Long) {
        if ((DataUtils.getCurrentTime() - (application.sharedPreferencesUtils.getDataUpdateTime())) > dataUpdateTimeLimit){
            val workManager = WorkManager.getInstance()
            setForecastDownload(workManager)
        }
    }

    private fun setForecastDownload(workManager: WorkManager) {
        val cityForecastWorker = OneTimeWorkRequestBuilder<ForecastWorker>()
            .build()
        workManager.enqueue(cityForecastWorker)
    }
}