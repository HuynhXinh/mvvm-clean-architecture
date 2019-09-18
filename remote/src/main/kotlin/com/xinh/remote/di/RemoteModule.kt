package com.xinh.remote.di

import com.google.gson.Gson
import org.koin.dsl.module

val remoteModule = module {
    single { Gson() }
}