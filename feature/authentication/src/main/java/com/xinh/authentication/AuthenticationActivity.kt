package com.xinh.authentication

import android.content.Context
import android.content.Intent
import com.xinh.authentication.login.listener.OnLoginClickBackListener
import com.xinh.authentication.login.listener.OnLoginSuccessListener
import com.xinh.authentication.login.listener.OnOpenRegisterListener
import com.xinh.koininjection.AUTHENTICATION_SCOPE_ID
import com.xinh.koininjection.AUTHENTICATION_SCOPE_NAME
import com.xinh.share.BaseSimpleActivity
import com.xinh.share.NavigatorActivity
import com.xinh.share.extension.getOrCreateScope
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.bindScope

class AuthenticationActivity : BaseSimpleActivity(),
    OnLoginClickBackListener,
    OnLoginSuccessListener,
    OnOpenRegisterListener {

    private val authenticationViewModelScope =
        getOrCreateScope(AUTHENTICATION_SCOPE_ID, AUTHENTICATION_SCOPE_NAME)

    private val navigator: NavigatorActivity by inject()

    override fun getLayout(): Int {
        return R.layout.activity_authentication
    }

    override fun onBindScope() {
        super.onBindScope()

        bindScope(authenticationViewModelScope)
    }

    override fun initView() {

    }

    override fun onSignInClickBack() {
        navigator.gotoSplash(this)
        finish()
    }

    override fun onLoginSuccess() {
        navigator.gotoMain(this)
        finish()
    }

    override fun openRegister() {
        navigator.gotoMain(this)
        finish()
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, AuthenticationActivity::class.java)
            context.startActivity(intent)
        }
    }
}