package com.xinh.presentation.account

import com.xinh.domain.manager.UserManager
import com.xinh.domain.model.UserType
import com.xinh.domain.repository.AccountRepository
import com.xinh.presentation.rx.SchedulerProvider

class AccountViewModuleImpl(
    schedulerProvider: SchedulerProvider,
    private val accountRepository: AccountRepository,
    private val userManager: UserManager
) : AccountViewModule(schedulerProvider) {

    override fun logout() {
        val user = userManager.getUser()

        if (user == null) {
            onLogoutSuccess.call()
            return
        }

        when (user.userType) {
            UserType.EMAIL -> logoutByToken(user.token)

            UserType.GOOGLE -> googleSignOut()

            UserType.FACEBOOK -> facebookLogout()
        }
    }

    private fun facebookLogout() {
        accountRepository.logoutFacebook()
            .execute({
                userManager.clear()

                onLogoutSuccess.call()
            }, {
                onLogoutFail.call()
            })
    }

    private fun googleSignOut() {
        accountRepository.logoutGoogle()
            .execute({
                userManager.clear()

                onLogoutSuccess.call()
            }, {
                onLogoutFail.call()
            })
    }

    private fun logoutByToken(token: String) {
        accountRepository.logoutByToken(token)
            .execute({
                userManager.clear()
                onLogoutSuccess.call()
            }, {
                onLogoutFail.call()
            })
    }
}