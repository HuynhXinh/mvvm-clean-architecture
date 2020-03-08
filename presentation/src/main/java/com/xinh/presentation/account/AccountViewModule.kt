package com.xinh.presentation.account

import com.xinh.domain.exception.Failure
import com.xinh.domain.executor.SchedulerProvider
import com.xinh.domain.interactor.Logout
import com.xinh.domain.interactor.UseCase
import com.xinh.domain.manager.UserManager
import com.xinh.presentation.BaseViewModel
import com.xinh.presentation.SingleLiveEvent

abstract class AccountViewModule(schedulerProvider: SchedulerProvider) : BaseViewModel(schedulerProvider) {

    val onLogoutSuccess = SingleLiveEvent<Unit>()
    val onLogoutFail = SingleLiveEvent<Unit>()

    abstract fun logout()
}

class AccountViewModuleImpl(
        schedulerProvider: SchedulerProvider,
        private val userManager: UserManager,
        private val logout: Logout
) : AccountViewModule(schedulerProvider) {

    override fun logout() {

        logout(Logout.Params(userManager.getToken(), userManager.getUser()?.userType)) {
            it.fold(::handelFailure, ::logoutSuccess)
        }
    }

    private fun logoutSuccess(none: UseCase.None) {
        userManager.clear()

        onLogoutSuccess.call()
    }

    private fun handelFailure(failure: Failure) {
        onLogoutFail.call()
    }
}