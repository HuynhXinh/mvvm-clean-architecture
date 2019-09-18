package com.xinh.travel.network

import android.content.Context
import com.xinh.remote.InvalidTokenListener
import com.xinh.remote.base.BaseResponse
import com.xinh.remote.error.BaseRemoteError
import com.xinh.remote.error.InvalidTokenError
import com.xinh.remote.error.NoInternetConnectionError
import com.xinh.remote.error.ResponseFailError
import com.xinh.remote.model.Error
import io.reactivex.*
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class RxErrorHandlingCallAdapterFactory(
    val context: Context
) : CallAdapter.Factory() {

    private val original: RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()
    private var invalidTokenListener: InvalidTokenListener? = null

    init {
        if (context is InvalidTokenListener) {
            invalidTokenListener = context
        }
    }

    override fun get(
        returnType: Type, annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<Any, Any> {
        return RxCallAdapterWrapper(
            returnType,
            (original.get(returnType, annotations, retrofit) as CallAdapter<Any, Any>?)!!
        )
    }

    inner class RxCallAdapterWrapper(
        private val returnType: Type,
        private val wrapped: CallAdapter<Any, Any>
    ) : CallAdapter<Any, Any> {

        override fun responseType(): Type {
            return wrapped.responseType()
        }

        override fun adapt(call: Call<Any>): Any? {
            val rawType = getRawType(returnType)

            val isFlowable = rawType == Flowable::class.java
            val isSingle = rawType == Single::class.java
            val isMaybe = rawType == Maybe::class.java
            val isCompletable = rawType == Completable::class.java
            if (rawType != Observable::class.java && !isFlowable && !isSingle && !isMaybe) {
                return null
            }
            if (returnType !is ParameterizedType) {
                val name = when {
                    isFlowable -> "Flowable"
                    isSingle -> "Single"
                    isMaybe -> "Maybe"
                    else -> "Observable"
                }
                throw IllegalStateException(
                    name
                            + " return type must be parameterized"
                            + " as "
                            + name
                            + "<Foo> or "
                            + name
                            + "<? extends Foo>"
                )
            }

            if (!InternetConnectionCheckerImpl(context).isNetworkAvailable()) {
                return handleNoInternetConnection(rawType)
            }

            if (isFlowable) {
                return (wrapped.adapt(call) as Flowable<*>)
                    .map { response -> handleResponse(response) }
                    .onErrorResumeNext { throwable: Throwable ->
                        Flowable.error(handleException(throwable))
                    }

            }
            if (isSingle) {
                return (wrapped.adapt(call) as Single<*>)
                    .map { response -> handleResponse(response) }
                    .onErrorResumeNext { throwable ->
                        Single.error(handleException(throwable))
                    }
            }
            if (isMaybe) {
                return (wrapped.adapt(call) as Maybe<*>)
                    .map { response -> handleResponse(response) }
                    .onErrorResumeNext { throwable: Throwable ->
                        Maybe.error(handleException(throwable))
                    }
            }
            if (isCompletable) {
                return (wrapped.adapt(call) as Completable)
                    .onErrorResumeNext { throwable ->
                        Completable.error(handleException(throwable))
                    }
            }
            return (wrapped.adapt(call) as Observable<*>)
                .map { response -> handleResponse(response) }
                .onErrorResumeNext { throwable: Throwable ->
                    Observable.error { handleException(throwable) }
                }
        }
    }


    private fun handleNoInternetConnection(rawType: Class<*>): Any {

        return when (rawType) {
            Flowable::class.java -> {
                Flowable.error<NoInternetConnectionError>(NoInternetConnectionError())
            }
            Single::class.java -> {
                Single.error<NoInternetConnectionError>(NoInternetConnectionError())
            }
            Maybe::class.java -> {
                Maybe.error<NoInternetConnectionError>(NoInternetConnectionError())
            }
            Completable::class.java -> {
                Completable.error(NoInternetConnectionError())
            }
            else -> {
                Observable.error<NoInternetConnectionError>(NoInternetConnectionError())
            }
        }
    }

    private fun handleException(throwable: Throwable): Throwable {
        when (throwable) {
            is InvalidTokenError -> {
                invalidTokenListener?.onInvalidToken()
            }
        }
        return throwable
    }

    private fun handleResponse(response: Any): Any {
        return if (response is Response<*>) {
            if (!isResponseSuccess(response)) {
                throw ResponseFailError()
            } else if (!isApiResponseSuccess(response)) {
                throw BaseRemoteError(getError(response))
            }

            response
        } else {
            response
        }
    }

    private fun isResponseSuccess(response: Response<*>): Boolean {
        return response.isSuccessful
    }

    private fun isApiResponseSuccess(response: Response<*>): Boolean {
        return getBaseResponse(response)?.status ?: true
    }

    private fun getError(response: Response<*>): Error? {
        return getBaseResponse(response)?.error
    }

    private fun getBaseResponse(response: Response<*>): BaseResponse<*>? {
        return response.body() as BaseResponse<*>?
    }

    companion object {
        fun create(
            context: Context
        ): CallAdapter.Factory {
            return RxErrorHandlingCallAdapterFactory(
                context = context
            )
        }
    }
}