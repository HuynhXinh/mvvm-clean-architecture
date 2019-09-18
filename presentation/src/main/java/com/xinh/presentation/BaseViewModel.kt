package com.xinh.presentation

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.github.ajalt.timberkt.Timber
import com.xinh.presentation.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel(
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {
    private val disposables = CompositeDisposable()

    protected fun execute(job: () -> Disposable) {
        disposables.add(job())
    }

    @CallSuper
    override fun onCleared() {
        disposables.clear()
        Timber.d { "Cleared viewModel called" }
        super.onCleared()
    }

    fun <T> Observable<T>.execute(
        onSuccess: ((T) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ) {
        disposables.add(
            subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    onSuccess?.invoke(it)
                }, {
                    Timber.d { "Error: $it" }
                    onError?.invoke(it)
                })
        )
    }

    fun <T> Single<T>.execute(
        onSuccess: ((T) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ) {
        disposables.add(
            subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    onSuccess?.invoke(it)
                }, {
                    Timber.d { "Error: $it" }
                    onError?.invoke(it)
                })
        )
    }
}