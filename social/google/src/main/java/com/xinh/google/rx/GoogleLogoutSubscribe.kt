package com.xinh.google.rx

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.xinh.google.BuildConfig.GOOGLE_SIGN_IN_CLIENT_ID
import com.xinh.google.exception.GoogleLogoutFailError
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe

class GoogleLogoutSubscribe(private val context: Context) :
    ObservableOnSubscribe<Boolean> {

    override fun subscribe(emitter: ObservableEmitter<Boolean>) {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(GOOGLE_SIGN_IN_CLIENT_ID)
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOption)

        googleSignInClient.signOut().addOnSuccessListener {
            if (!emitter.isDisposed) {
                emitter.onNext(true)
            }
        }

        googleSignInClient.signOut().addOnFailureListener {
            if (!emitter.isDisposed) {
                emitter.onError(GoogleLogoutFailError(it.message))
            }
        }

        googleSignInClient.signOut().addOnCanceledListener {
            if (!emitter.isDisposed) {
                emitter.setCancellable {
                    emitter.onNext(false)
                }
            }
        }
    }
}