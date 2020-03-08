package com.xinh.presentation

import com.xinh.domain.executor.SchedulerProvider
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
abstract class UnitTest {

    @Mock
    protected lateinit var schedulerProvider: SchedulerProvider
}