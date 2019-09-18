package com.xinh.presentation.authentication

import com.xinh.domain.manager.UserManager
import com.xinh.domain.model.User
import com.xinh.domain.param.LoginParam
import com.xinh.domain.repository.AuthenticationRepository
import com.xinh.presentation.BaseViewModelTest
import com.xinh.presentation.SingleLiveEvent
import com.xinh.presentation.mock
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class) // java kotlin
class AuthenticationViewModelTest : BaseViewModelTest() {

    lateinit var authenticationViewModel: AuthenticationViewModel

    @Mock
    lateinit var authenticationRepository: AuthenticationRepository

    @Mock
    lateinit var userManager: UserManager

    private var onLoginSuccess: SingleLiveEvent<User> = mock()
    private var onLoginFail: SingleLiveEvent<Unit> = mock()

    @Before
    override fun setUp() {
//        super.setUp()

        authenticationViewModel = AuthenticationViewModelImpl(
            testSchedulerProvider,
            authenticationRepository,
            userManager
        )

        authenticationViewModel.onLoginSuccess = onLoginSuccess
        authenticationViewModel.onLoginFail = onLoginFail
    }

    @Test
    fun `login email fail`() {
        //
        // Given
        //
        Mockito.`when`(authenticationRepository.login(anyString(), anyString()))
            .thenAnswer {
                Observable.error<Throwable> { Throwable() }
            }

        BDDMockito.given(authenticationRepository.login(anyString(), anyString()))
            .willAnswer {
                Observable.error<Throwable> { Throwable() }
            }

        //
        // When
        //
        authenticationViewModel.login(mockLoginEmailParam())

        //
        // Then
        //
        Mockito.verify(onLoginFail).call()
        Mockito.verifyNoMoreInteractions(userManager)
    }

    private fun mockLoginEmailParam(): LoginParam {
        return LoginParam(email = "test@gmail.com", password = "123")
    }

    @Test
    fun `login email success`() {
        val user = Mockito.mock(User::class.java)

        //
        // Given
        //
        BDDMockito.given(
            authenticationRepository.login(anyString(), anyString())
        ).willAnswer {
            Observable.just(user)
        }

        //
        // When
        //
        authenticationViewModel.login(mockLoginEmailParam())

        //
        // Then
        //
        Mockito.verify(onLoginSuccess).value = user
        Mockito.verify(userManager).saveUser(user)
    }

}