package com.xinh.share.extension

import android.view.View
import androidx.annotation.StringRes

fun View.getString(@StringRes resId: Int): String {
    return context.resources.getString(resId)
}

fun View.showOrGone(isShow: Boolean) {
    visibility = if (isShow) View.VISIBLE else View.GONE
}