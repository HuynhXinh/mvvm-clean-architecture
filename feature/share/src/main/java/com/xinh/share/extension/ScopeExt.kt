package com.xinh.share.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

fun AppCompatActivity.getOrCreateScope(scopeId: String, qualifier: String): Scope {
    return getKoin().getOrCreateScope(scopeId, named(qualifier))
}

fun Fragment.getOrCreateScope(scopeId: String, qualifier: String): Scope {
    return getKoin().getOrCreateScope(scopeId, named(qualifier))
}

inline fun <reified T : ViewModel> Fragment.getViewModelScope(
    scopeId: String,
    qualifier: String
): T? {
    return getOrCreateScope(scopeId, qualifier).get()
}

inline fun <reified T : ViewModel> Scope.getViewModelScope(): T? {
    return get()
}

