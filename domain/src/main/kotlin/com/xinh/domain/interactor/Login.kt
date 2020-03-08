package com.xinh.domain.interactor

import com.xinh.domain.exception.Failure
import com.xinh.domain.executor.SchedulerProvider
import com.xinh.domain.functional.Either
import com.xinh.domain.model.User
import com.xinh.domain.model.UserType
import com.xinh.domain.repository.UserRepository
import io.reactivex.Observable

class Login(
        schedulerProvider: SchedulerProvider,
        private val userRepository: UserRepository
) : UseCase<User, Login.Params>(schedulerProvider) {

    override fun buildObservable(params: Params): Observable<Either<Failure, User>> {
        return when (params.type) {
            UserType.Email -> userRepository.login(email = params.userName, password = params.password)

            UserType.Facebook -> userRepository.loginFacebook()

            UserType.Google -> userRepository.loginGoogle()
        }
    }

    data class Params(
            val userName: String = "",
            val password: String = "",
            val type: UserType = UserType.Email
    )
}