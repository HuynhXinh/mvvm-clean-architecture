package com.xinh.facebook

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.github.ajalt.timberkt.Timber
import com.xinh.facebook.rx.FacebookLoginSubscribe

class FacebookLoginActivity : Activity() {

    companion object {

        private const val ARG_ID = "id"

        fun start(context: Context, observableId: String) {
            val intent = Intent(context, FacebookLoginActivity::class.java).apply {
                putExtra(ARG_ID, observableId)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }
    }

    private var facebookCallbackManager: CallbackManager? = null
    private val facebookPermissions = listOf("public_profile", "email")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val observableId = intent.getStringExtra(ARG_ID)

        facebookCallbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance()
            .registerCallback(facebookCallbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    Timber.d { "Facebook login success ${result.accessToken}" }
                    FacebookLoginSubscribe.onLoginSuccess(
                        observableId,
                        result.accessToken
                    )

                    finish()
                }

                override fun onCancel() {
                    Timber.d { "Facebook login cancel...." }
                    FacebookLoginSubscribe.onLoginCanceled(observableId)

                    finish()
                }

                override fun onError(error: FacebookException) {
                    Timber.d { "Facebook error: $error" }
                    FacebookLoginSubscribe.onLoginFail(observableId, error.message)

                    finish()
                }
            })

        LoginManager.getInstance().logInWithReadPermissions(this, facebookPermissions)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        facebookCallbackManager?.onActivityResult(requestCode, resultCode, data)
    }
}