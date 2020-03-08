package com.xinh.travel.di

import com.xinh.domain.executor.SchedulerProvider
import com.xinh.presentation.rx.ThreadExecutor
import com.xinh.travel.rx.JobExecutor
import com.xinh.travel.rx.SchedulerProviderImpl
import org.koin.dsl.module

val appModule = module {
    single<ThreadExecutor> { JobExecutor() }

    single<SchedulerProvider> { SchedulerProviderImpl(threadExecutor = get()) }
}