package com.xinh.presentation.authentication

import com.xinh.domain.extension.safe
import com.xinh.domain.manager.UserManager
import com.xinh.domain.param.LoginParam
import com.xinh.domain.param.LoginTypeParam
import com.xinh.domain.repository.AuthenticationRepository
import com.xinh.presentation.rx.SchedulerProvider

class AuthenticationViewModelImpl(
    schedulerProvider: SchedulerProvider,
    private val authenticationRepository: AuthenticationRepository,
    private val userManager: UserManager
) : AuthenticationViewModel(schedulerProvider) {

    override fun login(loginParam: LoginParam) {
        when (loginParam.loginTypeParam) {
            LoginTypeParam.EMAIL -> loginEmail(loginParam.email, loginParam.password)

            LoginTypeParam.TOKEN -> loginByToken(loginParam.token)

            LoginTypeParam.FACEBOOK -> loginFacebook()

            LoginTypeParam.GOOGLE -> loginGoogle()
        }
    }

    private fun loginGoogle() {
        authenticationRepository.loginGoogle()
            .execute({
                userManager.saveUser(it)
                onLoginSuccess.value = it
            }, {
                onLoginFail.call()
            })
    }

    private fun loginFacebook() {
        authenticationRepository.loginFacebook()
            .execute({
                userManager.saveUser(it)
                onLoginSuccess.value = it
            }, {
                onLoginFail.call()
            })
    }

    private fun loginByToken(token: String?) {
        authenticationRepository.login(token.safe())
            .execute({
                userManager.saveUser(it)
                onLoginSuccess.value = it
            }, {
                onLoginFail.call()
            })
    }

    private fun loginEmail(email: String?, password: String?) {
        authenticationRepository.login(email.safe(), password.safe())
            .execute({
                userManager.saveUser(it)
                onLoginSuccess.value = it
            }, {
                onLoginFail.call()
            })
    }
}