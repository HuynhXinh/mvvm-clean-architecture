package com.xinh.share.extension

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleGone")
fun showHide(view: View, show: Boolean) {
    if (show) {
        view.show()
    } else {
        view.gone()
    }
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}