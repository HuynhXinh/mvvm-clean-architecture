package com.xinh.domain.interactor

import com.xinh.domain.exception.Failure
import com.xinh.domain.executor.SchedulerProvider
import com.xinh.domain.functional.Either
import com.xinh.domain.model.UserType
import com.xinh.domain.repository.UserRepository
import io.reactivex.Observable

class Logout(
        schedulerProvider: SchedulerProvider,
        private val userRepository: UserRepository
) : UseCase<UseCase.None, Logout.Params>(schedulerProvider) {

    override fun buildObservable(params: Params): Observable<Either<Failure, None>> {

        if (params.userType == null) return onDefaultSuccess()

        if (params.token == null) return onDefaultSuccess()

        return when (params.userType) {
            UserType.Email -> userRepository.logout(params.token).map { onLogoutSuccess() }

            UserType.Facebook -> userRepository.logoutFacebook().map { onLogoutSuccess() }

            UserType.Google -> userRepository.logoutGoogle().map { onLogoutSuccess() }
        }
    }

    private fun onDefaultSuccess(): Observable<Either<Failure, None>> {
        return Observable.just(Either.Right(None()))
    }

    private fun onLogoutSuccess(): Either<Failure, None>? {
        return Either.Right(None())
    }

    data class Params(
            val token: String?,
            val userType: UserType?
    )

}