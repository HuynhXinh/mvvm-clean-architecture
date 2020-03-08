package com.xinh.presentation

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.github.ajalt.timberkt.Timber
import com.xinh.domain.executor.SchedulerProvider
import com.xinh.domain.interactor.UseCase

abstract class BaseViewModel(private val schedulerProvider: SchedulerProvider, vararg useCases: UseCase<*, *>) : ViewModel() {
    private val useCases = listOf(*useCases)

    @CallSuper
    override fun onCleared() {
        Timber.d { "Cleared viewModel called" }
        useCases.forEach { it.dispose() }
        super.onCleared()
    }
}