package com.xinh.workermanager

import android.content.Context
import androidx.work.*
import com.github.ajalt.timberkt.Timber
import com.xinh.domain.repository.LocationRepository
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

class DetectLocationWorker(context: Context, workerParams: WorkerParameters) :
        RxWorker(context, workerParams), KoinComponent {

    private val locationRepository: LocationRepository by inject()

    companion object {
        const val DETECT_LOCATION_WORKER_NAME = "DetectLocationWorker"

        fun getRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<DetectLocationWorker>()
                    .setConstraints(getConstraints())
                    .build()
        }

        private fun getConstraints(): Constraints {
            return Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
        }
    }

    override fun createWork(): Single<Result> {
        return locationRepository.getCurrentLocation()
                .lastOrError()
                .doOnError {
                    Timber.d { "Detect location error: $it" }
                }
                .map {
                    Timber.d { "My location: $it" }
                    Result.success()
                }
    }

    override fun onStopped() {
        Timber.d { "onStopped..." }
        super.onStopped()
    }
}