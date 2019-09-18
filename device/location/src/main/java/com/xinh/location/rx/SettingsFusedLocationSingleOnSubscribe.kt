package com.xinh.location.rx

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import java.lang.ref.WeakReference
import java.util.*

class SettingsFusedLocationSingleOnSubscribe(
    private val context: Context,
    private val locationRequest: LocationRequest
) : SingleOnSubscribe<Boolean> {

    private var emitterWeakReference: WeakReference<SingleEmitter<Boolean>>? = null

    override fun subscribe(emitter: SingleEmitter<Boolean>) {
        emitterWeakReference = WeakReference(emitter)

        val settingsClient: SettingsClient = LocationServices.getSettingsClient(context)
        val locationSettingsRequest =
            LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .build()

        settingsClient.checkLocationSettings(locationSettingsRequest)
            .addOnSuccessListener { emitter.onSuccess(true) }
            .addOnFailureListener {
                val apiException = (it as ApiException)

                when (apiException.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        // All location settings are satisfied. The client can initialize location
                        // requests here.

                        val observableId = UUID.randomUUID().toString()
                        observableMap[observableId] =
                            WeakReference(this@SettingsFusedLocationSingleOnSubscribe)

                        val resolution = (it as ResolvableApiException).resolution

                        val intent = Intent(context, LocationSettingsActivity::class.java)
                        intent.putExtra(LocationSettingsActivity.ARG_ID, observableId)
                        intent.putExtra(LocationSettingsActivity.ARG_RESOLUTION, resolution)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(intent)
                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.

                        if (!emitter.isDisposed) {
                            emitter.onSuccess(false)
                        }
                    }

                    else -> {
                        if (!emitter.isDisposed) {
                            emitter.onError(SettingsFusedLocationException(apiException.message))
                        }
                    }
                }
            }
    }

    companion object {
        private val observableMap =
            HashMap<String, WeakReference<SettingsFusedLocationSingleOnSubscribe>>()

        fun onResolutionResult(observableId: String, resultCode: Int) {
            if (observableMap.containsKey(observableId)) {
                val observable = observableMap[observableId]?.get()
                observable?.emitterWeakReference?.get()?.onSuccess(resultCode == Activity.RESULT_OK)
                observableMap.remove(observableId)
            }
            observableMapCleanup()
        }

        private fun observableMapCleanup() {
            if (observableMap.isNotEmpty()) {
                val mutableIterator = observableMap.entries.iterator()

                while (mutableIterator.hasNext()) {
                    val entry = mutableIterator.next()
                    if (entry.value.get() == null) {
                        mutableIterator.remove()
                    }
                }
            }
        }
    }
}