package com.xinh.facebook.service

import android.content.Context
import android.os.Bundle
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.google.gson.Gson
import com.xinh.facebook.exception.FacebookGetUserFail
import com.xinh.facebook.model.FacebookAccessToken
import com.xinh.facebook.model.FacebookUser
import com.xinh.facebook.rx.FacebookLoginSubscribe
import io.reactivex.Observable

class FacebookServiceImpl(
    private val context: Context,
    private val gson: Gson
) : FacebookService {

    override fun loginToGetAccessToken(): Observable<FacebookAccessToken> {
        return Observable.create(FacebookLoginSubscribe(context))
            .map { FacebookAccessToken(it.token, it.userId) }
    }

    override fun loginToGetUser(): Observable<FacebookUser> {
        return Observable.create(FacebookLoginSubscribe(context))
            .flatMap {
                getUser(it)
            }
    }

    private fun getUser(accessToken: AccessToken): Observable<FacebookUser> {
        return Observable.create {
            val request = GraphRequest.newMeRequest(accessToken) { userJson, _ ->
                if (userJson != null) {
                    val user = gson.fromJson(userJson.toString(), FacebookUser::class.java)
                    if (!it.isDisposed) {
                        it.onNext(user)
                    }
                } else {
                    it.onError(FacebookGetUserFail())
                }
            }
            request.parameters = getParams()
            request.executeAsync()
        }
    }

    private fun getParams(): Bundle {
        return Bundle().apply {
            putString("fields", "id,name,email,gender,birthday")
        }
    }

    override fun logout(): Observable<Boolean> {
        return Observable.create {
            LoginManager.getInstance().logOut()
            it.onNext(true)
        }
    }
}