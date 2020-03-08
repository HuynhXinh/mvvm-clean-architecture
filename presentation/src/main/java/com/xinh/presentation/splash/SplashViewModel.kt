package com.xinh.presentation.splash

import com.xinh.domain.executor.SchedulerProvider
import com.xinh.presentation.BaseViewModel
import com.xinh.presentation.SingleLiveEvent

abstract class SplashViewModel(schedulerProvider: SchedulerProvider) :
    BaseViewModel(schedulerProvider) {

    val onGotoMain = SingleLiveEvent<Unit>()

    val onShowSplash = SingleLiveEvent<Unit>()

    abstract fun gotoMain()
}