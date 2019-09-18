package com.xinh.presentation.splash

import com.xinh.presentation.BaseViewModel
import com.xinh.presentation.SingleLiveEvent
import com.xinh.presentation.rx.SchedulerProvider

abstract class SplashViewModel(schedulerProvider: SchedulerProvider) :
    BaseViewModel(schedulerProvider) {

    val onGotoMain = SingleLiveEvent<Unit>()

    val onShowSplash = SingleLiveEvent<Unit>()

    abstract fun gotoMain()
}