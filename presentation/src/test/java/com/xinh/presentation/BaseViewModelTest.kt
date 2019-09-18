package com.xinh.presentation

import org.junit.Before
import org.mockito.MockitoAnnotations

abstract class BaseViewModelTest {

    protected var testSchedulerProvider = TestSchedulerProvider()

    @Before
    open fun setUp() {
        MockitoAnnotations.initMocks(this)
    }
}