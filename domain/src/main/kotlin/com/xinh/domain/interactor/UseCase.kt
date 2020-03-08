package com.xinh.domain.interactor

import com.xinh.domain.exception.Failure
import com.xinh.domain.exception.Failure.Unknown
import com.xinh.domain.exception.NoInternetException
import com.xinh.domain.executor.SchedulerProvider
import com.xinh.domain.functional.Either
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver

abstract class UseCase<Type, in Params>(private val schedulerProvider: SchedulerProvider) {
    private var disposable: Disposable? = null

    abstract fun buildObservable(params: Params): Observable<Either<Failure, Type>>

    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        disposable = buildObservable(params)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(object : DisposableObserver<Either<Failure, Type>>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: Either<Failure, Type>) {
                        onResult.invoke(t)
                    }

                    override fun onError(e: Throwable) {
                        onResult.invoke(handleException(e))
                    }
                })
    }

    private fun handleException(e: Throwable): Either<Failure, Type> {
        return when (e) {

            is NoInternetException -> Either.Left(Failure.NetworkConnection)

            else -> Either.Left(Unknown(e))
        }
    }

    fun dispose() {
        disposable?.let {
            if (it.isDisposed) it.dispose()
        }
    }

    class None
}