package com.xinh.google.service

import android.content.Context
import com.xinh.google.model.GoogleUser
import com.xinh.google.rx.GoogleLoginSubscribe
import com.xinh.google.rx.GoogleLogoutSubscribe
import io.reactivex.Observable

class GoogleServiceImpl(
    private val context: Context
) : GoogleService {
    override fun login(): Observable<GoogleUser> {
        return Observable.create(GoogleLoginSubscribe(context))
    }

    override fun logout(): Observable<Boolean> {
        return Observable.create(GoogleLogoutSubscribe(context))
    }
}