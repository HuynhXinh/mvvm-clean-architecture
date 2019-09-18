package com.xinh.presentation.di

import com.xinh.koininjection.ACCOUNT_SCOPE_NAME
import com.xinh.koininjection.AUTHENTICATION_SCOPE_NAME
import com.xinh.koininjection.SPLASH_SCOPE_NAME
import com.xinh.presentation.account.AccountViewModule
import com.xinh.presentation.account.AccountViewModuleImpl
import com.xinh.presentation.authentication.AuthenticationViewModel
import com.xinh.presentation.authentication.AuthenticationViewModelImpl
import com.xinh.presentation.splash.SplashViewModel
import com.xinh.presentation.splash.SplashViewModelImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val presentationModule = module {

    scope(named(SPLASH_SCOPE_NAME)) {
        scoped<SplashViewModel> {
            SplashViewModelImpl(
                schedulerProvider = get(),
                userManager = get()
            )
        }
    }

    scope(named(AUTHENTICATION_SCOPE_NAME)) {
        scoped<AuthenticationViewModel> {
            AuthenticationViewModelImpl(
                schedulerProvider = get(),
                authenticationRepository = get(),
                userManager = get()
            )
        }
    }

    scope(named(ACCOUNT_SCOPE_NAME)) {
        scoped<AccountViewModule> {
            AccountViewModuleImpl(
                schedulerProvider = get(),
                accountRepository = get(),
                userManager = get()
            )
        }
    }
}