package com.xinh.travel.rx

import com.xinh.presentation.rx.SchedulerProvider
import com.xinh.presentation.rx.ThreadExecutor
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerProviderImpl(private val threadExecutor: ThreadExecutor) : SchedulerProvider {
    override fun io(): Scheduler {
        return Schedulers.from(threadExecutor)
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}