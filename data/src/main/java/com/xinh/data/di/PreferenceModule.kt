package com.xinh.data.di

import com.xinh.data.preference.SharedPreferenceStorage
import com.xinh.data.repository.user.disk.UserPreference
import com.xinh.data.repository.user.disk.UserPreferenceImpl
import org.koin.dsl.module

val preferenceModule = module {
    single { SharedPreferenceStorage.create(context = get()) }

    single<UserPreference> { UserPreferenceImpl(prefs = get()) }
}