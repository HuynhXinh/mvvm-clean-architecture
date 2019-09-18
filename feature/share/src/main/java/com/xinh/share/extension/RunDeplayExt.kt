package com.xinh.share.extension

import android.app.Activity
import android.os.Handler
import androidx.fragment.app.Fragment
import java.util.concurrent.TimeUnit

fun Activity.runDelayed(delay: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS, f: () -> Unit) {
    Handler().postDelayed({
        if (!isFinishing) {
            f.invoke()
        }
    }, timeUnit.toMillis(delay))
}

fun Fragment.runDelayed(delay: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS, f: () -> Unit) {
    Handler().postDelayed({
        if (isVisible) {
            f.invoke()
        }
    }, timeUnit.toMillis(delay))
}