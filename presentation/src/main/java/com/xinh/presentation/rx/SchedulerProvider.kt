package com.xinh.presentation.rx

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun io(): Scheduler

    fun ui(): Scheduler
}