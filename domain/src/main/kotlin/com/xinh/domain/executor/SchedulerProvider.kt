package com.xinh.domain.executor

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun io(): Scheduler

    fun ui(): Scheduler
}