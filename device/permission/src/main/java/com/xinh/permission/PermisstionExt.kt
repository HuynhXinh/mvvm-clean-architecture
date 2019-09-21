package com.xinh.permission

import android.app.Activity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

fun Activity.requestPermissionsExt(permissionToAsk: Array<String>, key: Int) {
    ActivityCompat.requestPermissions(this, permissionToAsk, key)
}

fun Fragment.requestPermissionsExt(permissionToAsk: Array<String>, key: Int) {
    this.requestPermissions(permissionToAsk, key)
}