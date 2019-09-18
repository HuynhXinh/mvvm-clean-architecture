package com.xinh.google.rx

import android.content.Context
import com.xinh.google.GoogleLoginActivity
import com.xinh.google.exception.GoogleLoginFailError
import com.xinh.google.model.GoogleUser
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import java.lang.ref.WeakReference
import java.util.*

class GoogleLoginSubscribe(private val context: Context) : ObservableOnSubscribe<GoogleUser> {

    private var emitterWeakReference: WeakReference<ObservableEmitter<GoogleUser>>? = null

    companion object {
        private val observableMap = HashMap<String, WeakReference<GoogleLoginSubscribe>>()

        fun onSignInSuccess(observableId: String, googleUser: GoogleUser?) {
            if (googleUser == null) {
                getObservable(observableId)
                    ?.onError(IllegalArgumentException("GoogleUser is null"))
                return
            }

            getObservable(observableId)?.onNext(googleUser)

            clearAllObservable()
        }

        fun onSignInFail(observableId: String, message: String?) {
            getObservable(observableId)?.onError(
                GoogleLoginFailError(
                    message
                )
            )

            clearAllObservable()
        }

        private fun getObservable(observableId: String): ObservableEmitter<GoogleUser>? {
            return if (observableMap.containsKey(observableId)) {
                val emitter = observableMap[observableId]?.get()?.emitterWeakReference?.get()

                if (emitter?.isDisposed == true) return null

                emitter
            } else {
                null
            }
        }

        private fun clearAllObservable() {
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

    override fun subscribe(emitter: ObservableEmitter<GoogleUser>) {
        emitterWeakReference = WeakReference(emitter)

        val observableId = UUID.randomUUID().toString()
        observableMap[observableId] = WeakReference(this@GoogleLoginSubscribe)

        GoogleLoginActivity.start(context, observableId)
    }
}