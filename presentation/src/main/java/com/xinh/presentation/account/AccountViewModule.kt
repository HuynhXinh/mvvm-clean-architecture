package com.xinh.presentation.account

import com.xinh.presentation.BaseViewModel
import com.xinh.presentation.SingleLiveEvent
import com.xinh.presentation.rx.SchedulerProvider

abstract class AccountViewModule(
    schedulerProvider: SchedulerProvider
) : BaseViewModel(schedulerProvider) {

    val onLogoutSuccess = SingleLiveEvent<Unit>()
    val onLogoutFail = SingleLiveEvent<Unit>()

    abstract fun logout()
}