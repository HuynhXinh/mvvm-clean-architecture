package com.xinh.workermanager

import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager

interface DetectLocation {
    fun startDetectLocation()
}

class DetectLocationImpl(private val workManager: WorkManager) : DetectLocation {
    override fun startDetectLocation() {
        workManager.beginUniqueWork(
            DetectLocationWorker.DETECT_LOCATION_WORKER_NAME,
            ExistingWorkPolicy.REPLACE,
            DetectLocationWorker.getRequest()
        ).enqueue()
    }
}