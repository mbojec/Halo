package com.mbojec.halo

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

object WorkerManager {
    fun startSyncDataDownload() {
        val workManager = WorkManager.getInstance()
        setForecastDownload(workManager)
    }

    private fun setForecastDownload(workManager: WorkManager) {
        val cityForecastWorker = OneTimeWorkRequestBuilder<ForecastWorker>()
            .build()
        workManager.enqueue(cityForecastWorker)
    }
}