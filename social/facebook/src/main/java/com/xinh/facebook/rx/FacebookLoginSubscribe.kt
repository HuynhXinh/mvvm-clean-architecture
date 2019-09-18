package com.xinh.facebook.rx

import android.content.Context
import com.facebook.AccessToken
import com.xinh.facebook.FacebookLoginActivity
import com.xinh.facebook.exception.FacebookCanceled
import com.xinh.facebook.exception.FacebookLoginFail
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import java.lang.ref.WeakReference
import java.util.*

class FacebookLoginSubscribe(private val context: Context) :
    ObservableOnSubscribe<AccessToken> {

    private var emitterWeakReference: WeakReference<ObservableEmitter<AccessToken>>? = null

    companion object {
        private val observableMap =
            HashMap<String, WeakReference<FacebookLoginSubscribe>>()

        fun onLoginCanceled(observableId: String) {
            getObservable(observableId)?.onError(FacebookCanceled())

            clearAllObservable()
        }

        fun onLoginFail(observableId: String, msg: String?) {
            getObservable(observableId)?.onError(FacebookLoginFail(msg))

            clearAllObservable()
        }

        fun onLoginSuccess(observableId: String, accessToken: AccessToken) {
            getObservable(observableId)?.onNext(accessToken)

            clearAllObservable()
        }

        private fun getObservable(observableId: String): ObservableEmitter<AccessToken>? {
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

    override fun subscribe(emitter: ObservableEmitter<AccessToken>) {
        emitterWeakReference = WeakReference(emitter)

        val observableId = UUID.randomUUID().toString()
        observableMap[observableId] = WeakReference(this@FacebookLoginSubscribe)

        FacebookLoginActivity.start(context, observableId)
    }
}