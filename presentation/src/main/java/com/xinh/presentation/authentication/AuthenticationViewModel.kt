package com.xinh.presentation.authentication

import com.xinh.domain.model.User
import com.xinh.domain.param.LoginParam
import com.xinh.presentation.BaseViewModel
import com.xinh.presentation.SingleLiveEvent
import com.xinh.presentation.rx.SchedulerProvider

abstract class AuthenticationViewModel(schedulerProvider: SchedulerProvider) :
    BaseViewModel(schedulerProvider) {

    var onLoginSuccess = SingleLiveEvent<User>()
    var onLoginFail = SingleLiveEvent<Unit>()

    abstract fun login(loginParam: LoginParam)
}