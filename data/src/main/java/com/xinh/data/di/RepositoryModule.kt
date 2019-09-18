package com.xinh.data.di

import com.xinh.data.repository.AccountRepositoryImpl
import com.xinh.data.repository.AuthenticationRepositoryImpl
import com.xinh.domain.repository.AccountRepository
import com.xinh.domain.repository.AuthenticationRepository
import com.xinh.facebook.service.FacebookService
import com.xinh.facebook.service.FacebookServiceImpl
import com.xinh.google.service.GoogleService
import com.xinh.google.service.GoogleServiceImpl
import com.xinh.koininjection.ACCOUNT_SCOPE_NAME
import com.xinh.koininjection.AUTHENTICATION_SCOPE_NAME
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {

    scope(named(AUTHENTICATION_SCOPE_NAME)) {
        scoped<GoogleService> { GoogleServiceImpl(context = get()) }
        scoped<FacebookService> { FacebookServiceImpl(context = get(), gson = get()) }

        scoped<AuthenticationRepository> {
            AuthenticationRepositoryImpl(
                authenticationService = get(),
                googleService = get(),
                facebookService = get()
            )
        }
    }

    scope(named(ACCOUNT_SCOPE_NAME)) {
        scoped<GoogleService> { GoogleServiceImpl(context = get()) }
        scoped<FacebookService> { FacebookServiceImpl(context = get(), gson = get()) }

        scoped<AccountRepository> {
            AccountRepositoryImpl(
                authenticationService = get(),
                googleService = get(),
                facebookService = get()
            )
        }
    }
}