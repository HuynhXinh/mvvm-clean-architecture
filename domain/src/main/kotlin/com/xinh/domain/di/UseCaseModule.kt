package com.xinh.domain.di

import com.xinh.domain.interactor.Login
import com.xinh.domain.interactor.Logout
import org.koin.dsl.module

val useCaseModule = module {
    factory { Login(schedulerProvider = get(), userRepository = get()) }

    factory { Logout(schedulerProvider = get(), userRepository = get()) }
}