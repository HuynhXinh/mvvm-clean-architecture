package com.xinh.data.repository

import UnitTest
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.xinh.data.repository.user.cloud.AuthenticationService
import com.xinh.data.repository.user.cloud.LoginRequest
import com.xinh.data.repository.user.cloud.LoginTokenRequest
import com.xinh.data.repository.user.cloud.LogoutRequest
import com.xinh.domain.functional.Either
import com.xinh.domain.repository.UserRepository
import com.xinh.facebook.model.FacebookUser
import com.xinh.facebook.service.FacebookService
import com.xinh.google.model.GoogleUser
import com.xinh.google.service.GoogleService
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import java.net.HttpURLConnection.HTTP_BAD_REQUEST

class UserRepositoryTest : UnitTest() {

    private lateinit var userRepository: UserRepository

    private val authenticationService: AuthenticationService =
        create(AuthenticationService::class.java)
    private val spyAuthenticationService = spy(authenticationService)

    @Mock
    private lateinit var googleService: GoogleService

    @Mock
    private lateinit var facebookService: FacebookService

    @Before
    fun setup() {
        userRepository = UserRepositoryImpl(
            authenticationService = spyAuthenticationService,
            googleService = googleService,
            facebookService = facebookService
        )
    }

    @Test
    fun `login by email success`() {
        mockResponse("loginEmail_whenSuccess.json")

        val test = userRepository.login("email", "123").test()

        verify(spyAuthenticationService).login(LoginRequest("email", "123"))

        test.assertComplete()
        test.assertValue {
            it is Either.Right
        }
    }

    @Test
    fun `login by email fail`() {
        mockResponse("loginEmail_whenFail.json")

        val test = userRepository.login("email", "123").test()

        verify(spyAuthenticationService).login(LoginRequest("email", "123"))

        test.assertComplete()
        test.assertValue {
            it is Either.Left
        }
    }

    @Test
    fun `login by token`() {
        mockResponse("loginEmail_whenSuccess.json")

        val test = userRepository.login("token").test()

        verify(spyAuthenticationService).login(LoginTokenRequest("token"))

        test.assertComplete()
        test.assertValue {
            it is Either.Right
        }
    }

    @Test
    fun `login by token fail`() {
        mockResponse("loginEmail_whenSuccess.json", HTTP_BAD_REQUEST)

        val test = userRepository.login("token").test()

        verify(spyAuthenticationService).login(LoginTokenRequest("token"))

        test.assertComplete()
        test.assertValue {
            it is Either.Left
        }
    }

    @Test
    fun `logout by token`() {
        mockResponse("logoutByToken_whenSuccess.json")

        val test = userRepository.logout("token").test()

        verify(spyAuthenticationService).logout(LogoutRequest("token"))

        test.assertComplete()
        test.assertValue {
            it is Either.Right
        }
    }

    @Test
    fun `login by facebook`() {
        `when`(facebookService.login()).thenReturn(Observable.just(FacebookUser()))

        val test = userRepository.loginFacebook().test()

        verify(facebookService).login()
        test.assertComplete()
        test.assertValue {
            it is Either.Right
        }
    }

    @Test
    fun `logout by facebook`() {
        `when`(facebookService.logout()).thenReturn(Observable.just(true))

        val test = userRepository.logoutFacebook().test()

        verify(facebookService).logout()
        test.assertComplete()
        test.assertValue {
            it is Either.Right
        }
    }

    @Test
    fun `login google`() {
        `when`(googleService.login()).thenReturn(Observable.just(GoogleUser()))

        val test = userRepository.loginGoogle().test()

        verify(googleService).login()
        test.assertComplete()
        test.assertValue {
            it is Either.Right
        }
    }

    @Test
    fun `logout google`() {
        `when`(googleService.logout()).thenReturn(Observable.just(true))

        val test = userRepository.logoutGoogle().test()

        verify(googleService).logout()
        test.assertComplete()
        test.assertValue {
            it is Either.Right
        }
    }
}