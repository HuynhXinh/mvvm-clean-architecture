package com.xinh.travel.di

import com.xinh.data.manager.UserManagerImpl
import com.xinh.domain.manager.UserManager
import org.koin.dsl.module

val managerModule = module {

    single<UserManager> { UserManagerImpl(userPreference = get(), gson = get()) }
}