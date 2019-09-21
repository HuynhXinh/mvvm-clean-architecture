package com.xinh.workermanager.di

import androidx.work.WorkManager
import com.xinh.workermanager.DetectLocation
import com.xinh.workermanager.DetectLocationImpl
import org.koin.dsl.module

val workerManagerModule = module {
    single { WorkManager.getInstance(get()) }

    single<DetectLocation> { DetectLocationImpl(workManager = get()) }
}