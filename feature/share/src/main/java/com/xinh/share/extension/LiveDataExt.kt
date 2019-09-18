package com.xinh.share.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeExt(fragment: Fragment, function: (T) -> Unit) {
    observe(fragment.viewLifecycleOwner, Observer {
        function(it)
    })
}

fun <T> LiveData<T>.observeExt(activity: AppCompatActivity, function: (T) -> Unit) {
    observe(activity, Observer {
        function(it)
    })
}