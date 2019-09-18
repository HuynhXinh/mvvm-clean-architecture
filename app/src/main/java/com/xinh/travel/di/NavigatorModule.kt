package com.xinh.travel.di

import com.xinh.share.NavigatorActivity
import com.xinh.travel.NavigatorActivityImpl
import org.koin.dsl.module

val navigatorModule = module {
    single<NavigatorActivity> { NavigatorActivityImpl() }
}