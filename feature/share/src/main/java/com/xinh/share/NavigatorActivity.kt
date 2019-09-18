package com.xinh.share

import android.content.Context

interface NavigatorActivity {
    fun gotoSplash(context: Context)

    fun gotoMain(context: Context)

    fun gotoAuthentication(context: Context)
}