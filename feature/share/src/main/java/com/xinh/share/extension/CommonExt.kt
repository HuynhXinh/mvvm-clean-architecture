package com.xinh.share.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.convertDpToPixel(dimension: Int): Int {
    return resources.getDimensionPixelSize(dimension)
}

fun showSoftKeyboard(view: View?) {
    if (view?.requestFocus() == true) {
        val imm =
            view.context?.applicationContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun hideSoftKeyboard(view: View?) {
    view?.run {
        val imm =
            this.context.applicationContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Int?.safe(default: Int = 0): Int {
    return this ?: default
}

@ColorInt
fun Context.getCompatColor(@ColorRes resId: Int): Int {
    return ContextCompat.getColor(this, resId)
}