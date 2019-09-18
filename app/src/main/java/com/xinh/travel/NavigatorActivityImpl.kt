package com.xinh.travel

import android.content.Context
import com.xinh.authentication.AuthenticationActivity
import com.xinh.share.NavigatorActivity
import com.xinh.splash.SplashActivity

class NavigatorActivityImpl : NavigatorActivity {
    override fun gotoSplash(context: Context) {
        SplashActivity.start(context)
    }

    override fun gotoMain(context: Context) {
        MainActivity.start(context)
    }

    override fun gotoAuthentication(context: Context) {
        AuthenticationActivity.start(context)
    }
}