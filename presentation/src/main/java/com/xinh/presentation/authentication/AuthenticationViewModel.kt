package com.xinh.presentation.authentication

import com.xinh.domain.exception.Failure
import com.xinh.domain.executor.SchedulerProvider
import com.xinh.domain.interactor.Login
import com.xinh.domain.manager.UserManager
import com.xinh.domain.model.User
import com.xinh.presentation.BaseViewModel
import com.xinh.presentation.SingleLiveEvent

abstract class AuthenticationViewModel(schedulerProvider: SchedulerProvider, val login: Login) :
        BaseViewModel(schedulerProvider, login) {

    var onLoginSuccess = SingleLiveEvent<User>()
    var onLoginFail = SingleLiveEvent<Unit>()

    abstract fun login(params: Login.Params)
}

class AuthenticationViewModelImpl(
        schedulerProvider: SchedulerProvider,
        private val userManager: UserManager,
        login: Login
) : AuthenticationViewModel(schedulerProvider, login) {

    override fun login(params: Login.Params) {
        login(params) {
            it.fold(::handleFailure, ::loginSuccess)
        }
    }

    private fun loginSuccess(user: User) {
        userManager.saveUser(user)

        onLoginSuccess.call()
    }

    private fun handleFailure(failure: Failure) {
        onLoginFail.call()
    }
}