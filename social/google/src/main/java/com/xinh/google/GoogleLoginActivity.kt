package com.xinh.google

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.ajalt.timberkt.Timber
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.xinh.google.BuildConfig.GOOGLE_SIGN_IN_CLIENT_ID
import com.xinh.google.model.GoogleUser
import com.xinh.google.rx.GoogleLoginSubscribe

class GoogleLoginActivity : Activity() {

    companion object {
        const val REQUEST_CODE_GOOGLE_SIGN_IN = 1000

        private const val ARG_ID = "id"

        fun start(context: Context, observableId: String) {
            val intent = Intent(context, GoogleLoginActivity::class.java).apply {
                putExtra(ARG_ID, observableId)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(GOOGLE_SIGN_IN_CLIENT_ID)
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOption)

        startActivityForResult(googleSignInClient?.signInIntent, REQUEST_CODE_GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_GOOGLE_SIGN_IN) {
            handleGoogleSignIn(data)
            finish()
        }
    }

    private fun handleGoogleSignIn(data: Intent?) {
        val observableId = intent.getStringExtra(ARG_ID)
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            if (task.isSuccessful) {
                val googleUser = toGoogleUser(task.result)
                GoogleLoginSubscribe.onSignInSuccess(observableId, googleUser)
            } else {
                signInFail(observableId, task.exception?.message)
            }
        } catch (ex: ApiException) {
            signInFail(observableId, ex.message)
        }
    }

    private fun signInFail(observableId: String, msg: String?) {
        Timber.d { "Google SignIn fail $msg" }
        GoogleLoginSubscribe.onSignInFail(observableId, msg)
    }

    private fun toGoogleUser(result: GoogleSignInAccount?): GoogleUser? {
        return result?.let {
            GoogleUser(
                id = it.id,
                token = it.idToken,
                firstName = it.givenName,
                lastName = it.familyName
            )
        }
    }
}