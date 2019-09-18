package com.xinh.travel.rx

import com.xinh.presentation.rx.ThreadExecutor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

open class JobExecutor : ThreadExecutor {

    private val workQueue: LinkedBlockingQueue<Runnable> = LinkedBlockingQueue()

    private val threadPoolExecutor: ThreadPoolExecutor

    private val threadFactory: ThreadFactory

    init {
        this.threadFactory = JobThreadFactory()
        this.threadPoolExecutor = ThreadPoolExecutor(
            INITIAL_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_TIME.toLong(),
            KEEP_ALIVE_TIME_UNIT, this.workQueue, this.threadFactory
        )
    }

    override fun execute(runnable: Runnable?) {
        requireNotNull(runnable) { "Runnable to execute cannot be null" }
        this.threadPoolExecutor.execute(runnable)
    }

    private class JobThreadFactory : ThreadFactory {
        private var counter = 0

        override fun newThread(runnable: Runnable): Thread {
            return Thread(runnable, THREAD_NAME + counter++)
        }

        companion object {
            private const val THREAD_NAME = "android_"
        }
    }

    companion object {

        private const val INITIAL_POOL_SIZE = 3
        private const val MAX_POOL_SIZE = 5

        // Sets the amount of time an idle thread waits before terminating
        private const val KEEP_ALIVE_TIME = 10

        // Sets the Time Unit to seconds
        private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS
    }
}