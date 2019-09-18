package com.xinh.presentation.splash

import com.xinh.domain.manager.UserManager
import com.xinh.presentation.rx.SchedulerProvider

class SplashViewModelImpl(
    schedulerProvider: SchedulerProvider,
    private val userManager: UserManager
) : SplashViewModel(schedulerProvider) {

    override fun gotoMain() {
        if (userManager.getUser() != null) {
            onGotoMain.call()
        } else {
            onShowSplash.call()
        }
    }
}