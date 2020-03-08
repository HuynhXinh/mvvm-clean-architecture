package com.xinh.travel.di

import android.content.Context
import com.google.gson.Gson
import com.xinh.data.rx.NetworkHandler
import com.xinh.data.rx.NetworkHandlerImpl
import com.xinh.data.rx.RxAdapterFactory
import com.xinh.domain.manager.UserManager
import com.xinh.travel.BuildConfig
import com.xinh.travel.config.API_URL
import com.xinh.travel.config.APP_NAME
import com.xinh.travel.config.CLIENT_ID
import com.xinh.travel.di.NameInjection.NAME_HEADER_INTERCEPTOR
import com.xinh.travel.di.NameInjection.NAME_LOGGING_INTERCEPTOR
import com.xinh.travel.di.Properties.CLIENT
import com.xinh.travel.di.Properties.DEVICE_OS
import com.xinh.travel.di.Properties.DEVICE_TYPE
import com.xinh.travel.di.Properties.LANG
import com.xinh.travel.di.Properties.TIME_OUT
import com.xinh.travel.di.Properties.TOKEN
import com.xinh.travel.di.Properties.USER_AGENT
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single { Gson() }

    single {
        getRetrofit(
                okHttpClient = get(),
                gsonFactory = get(),
                rxJavaFactory = get()
        )
    }

    single { getOkHttpCache(context = androidApplication()) }

    single {
        getOkHttpClient(
                cache = get(),
                logging = get(named(NAME_LOGGING_INTERCEPTOR)),
                header = get(named(NAME_HEADER_INTERCEPTOR))
        )
    }

    single(named(NAME_LOGGING_INTERCEPTOR)) { getLoggingInterceptor() }

    single(named(NAME_HEADER_INTERCEPTOR)) {
        getHeaderInterceptor(
                userManager = get()
        )
    }

    single { getGsonConverterFactory(gson = get()) }

    single<NetworkHandler> { NetworkHandlerImpl(context = get()) }

    single {
        getRxAdapterFactory(networkHandler = get())
    }
}

object NameInjection {
    const val NAME_LOGGING_INTERCEPTOR = "logging"
    const val NAME_HEADER_INTERCEPTOR = "header"
}

object Properties {
    const val TIME_OUT = 60L // seconds
    const val LANG = "Lang"
    const val CLIENT = "Client"
    const val TOKEN = "x-token"
    const val USER_AGENT = "User-Agent"
    const val DEVICE_OS = "device-os"
    const val DEVICE_TYPE = "device-type"
}

fun getRetrofit(
        okHttpClient: OkHttpClient,
        gsonFactory: GsonConverterFactory,
        rxJavaFactory: CallAdapter.Factory
): Retrofit {
    return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(gsonFactory)
            .addCallAdapterFactory(rxJavaFactory)
            .client(okHttpClient)
            .build()
}

fun getOkHttpCache(context: Context): Cache {
    val size = (10 * 1024 * 1024).toLong() // 10 Mb
    return Cache(context.cacheDir, size)
}

fun getLoggingInterceptor(): Interceptor {
    val logging = HttpLoggingInterceptor()
    HttpLoggingInterceptor.Level.BODY
    return logging
}

fun getHeaderInterceptor(userManager: UserManager): Interceptor {
    return Interceptor { chain ->

        val requestBuilder = chain.request().newBuilder()
                .addHeader(CLIENT, CLIENT_ID)
                .addHeader(LANG, "en")
                .addHeader(USER_AGENT, getUserAgent())
                .addHeader(DEVICE_OS, "Android")
                .addHeader(DEVICE_TYPE, "Android")

        userManager.getToken()?.let {
            requestBuilder.addHeader(TOKEN, it)
        }

        chain.proceed(requestBuilder.build())
    }
}

fun getUserAgent(): String {
    return "$APP_NAME/${BuildConfig.VERSION_NAME} ${System.getProperty(
            "http.agent"
    )
            ?: "N/A"}"
}

fun getOkHttpClient(
        cache: Cache,
        logging: Interceptor,
        header: Interceptor
): OkHttpClient {

    val okHttpClientBuilder = OkHttpClient.Builder()
    okHttpClientBuilder.apply {
        addInterceptor(header)
        addInterceptor(logging)
        connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        readTimeout(TIME_OUT, TimeUnit.SECONDS)
        cache(cache)
    }
    return okHttpClientBuilder.build()
}


fun getGsonConverterFactory(gson: Gson): GsonConverterFactory {
    return GsonConverterFactory.create(gson)
}

fun getRxAdapterFactory(networkHandler: NetworkHandler): CallAdapter.Factory {
    return RxAdapterFactory(networkHandler)
}
