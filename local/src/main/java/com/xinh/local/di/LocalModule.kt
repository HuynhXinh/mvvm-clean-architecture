package com.xinh.local.di

import com.xinh.local.SharedPreferenceStorage
import com.xinh.local.authentication.UserPreference
import com.xinh.local.authentication.UserPreferenceImpl
import org.koin.dsl.module

val localModule = module {
    single { SharedPreferenceStorage.create(context = get()) }

    single<UserPreference> { UserPreferenceImpl(prefs = get()) }
}