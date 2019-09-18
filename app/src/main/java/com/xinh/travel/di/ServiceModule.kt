package com.xinh.travel.di

import com.xinh.remote.service.AuthenticationService
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {

    single { provideLoginService(retrofit = get()) }

}

private fun provideLoginService(retrofit: Retrofit): AuthenticationService {
    return retrofit.create(AuthenticationService::class.java)
}
