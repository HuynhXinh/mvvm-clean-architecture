package com.xinh.splash

import android.content.Context
import android.content.Intent
import android.view.View
import com.xinh.koininjection.SPLASH_SCOPE_ID
import com.xinh.koininjection.SPLASH_SCOPE_NAME
import com.xinh.presentation.splash.SplashViewModel
import com.xinh.share.BaseBindingActivity
import com.xinh.share.NavigatorActivity
import com.xinh.share.extension.fadeIn
import com.xinh.share.extension.getOrCreateScope
import com.xinh.share.extension.getViewModelScope
import com.xinh.share.extension.observeExt
import com.xinh.splash.databinding.ActivitySplashBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.bindScope

class SplashActivity : BaseBindingActivity<ActivitySplashBinding>() {

    private val splashViewModelScope =
        getOrCreateScope(SPLASH_SCOPE_ID, SPLASH_SCOPE_NAME)

    private val splashViewModel =
        splashViewModelScope.getViewModelScope<SplashViewModel>()

    private val navigator: NavigatorActivity by inject()

    override fun getLayout(): Int {
        return R.layout.activity_splash
    }

    override fun onBindScope() {
        super.onBindScope()
        bindScope(splashViewModelScope)
    }

    override fun initView() {
        splashViewModel?.gotoMain()
    }

    override fun intiObserveViewModel() {
        splashViewModel?.run {
            onGotoMain.observeExt(this@SplashActivity) {
                gotoMainScreen()
            }

            onShowSplash.observeExt(this@SplashActivity) {
                onShowSplashScreen()
            }
        }
    }

    private fun gotoMainScreen() {
        navigator.gotoMain(this@SplashActivity)
        finish()
    }

    private fun onShowSplashScreen() {
        viewBinding?.run {
            tvExplore.fadeIn()
            tvExploreDescription.fadeIn()
            tvSignInSignUp.fadeIn()
            tvTrial.fadeIn()

            onSignInSignUpClickListener = View.OnClickListener {
                navigator.gotoAuthentication(this@SplashActivity)
                finish()
            }

            onTryExploreClickListener = View.OnClickListener {
                gotoMainScreen()
            }
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SplashActivity::class.java)
            context.startActivity(intent)
        }
    }
}
