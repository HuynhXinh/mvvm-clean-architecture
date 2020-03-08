package com.xinh.presentation.authentication

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.xinh.domain.exception.Failure
import com.xinh.domain.functional.Either
import com.xinh.domain.interactor.Login
import com.xinh.domain.manager.UserManager
import com.xinh.domain.model.User
import com.xinh.presentation.SingleLiveEvent
import com.xinh.presentation.UnitTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock

class AuthenticationViewModelTest : UnitTest() {

    private lateinit var authenticationViewModel: AuthenticationViewModel

    @Mock
    private lateinit var userManager: UserManager

    @Mock
    private lateinit var login: Login

    private var onLoginSuccess: SingleLiveEvent<User> = mock()
    private var onLoginFail: SingleLiveEvent<Unit> = mock()

    @Before
    fun setUp() {
        authenticationViewModel = AuthenticationViewModelImpl(
            schedulerProvider,
            userManager,
            login
        )

        authenticationViewModel.onLoginSuccess = onLoginSuccess
        authenticationViewModel.onLoginFail = onLoginFail
    }

    @Test
    fun `login email fail`() {
        //
        // When
        //
        authenticationViewModel.login(mockLoginEmailParam())

        //
        // Given
        //
        val params = argumentCaptor<Login.Params>()
        val onResult = argumentCaptor<(Either<Failure, User>) -> Unit>()
        verify(login).invoke(params.capture(), onResult.capture())

        onResult.firstValue.invoke(Either.Left(Failure.ServerError()))

        //
        // Then
        //
        verify(onLoginFail).call()

    }

    private fun mockLoginEmailParam(): Login.Params {
        return Login.Params("xinh@gmail.com", "12345")
    }

    @Test
    fun `login email success`() {
        val user = mock(User::class.java)

        //
        // When
        //
        authenticationViewModel.login(mockLoginEmailParam())

        //
        // Given
        //
        val params = argumentCaptor<Login.Params>()
        val onResult = argumentCaptor<(Either<Failure, User>) -> Unit>()
        verify(login).invoke(params.capture(), onResult.capture())
        onResult.firstValue.invoke(Either.Right(user))

        //
        // Then
        //
        verify(userManager).saveUser(user)
        verify(onLoginSuccess).call()
    }

}