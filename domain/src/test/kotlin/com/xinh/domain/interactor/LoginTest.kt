package com.xinh.domain.interactor

import UnitTest
import com.nhaarman.mockitokotlin2.verify
import com.xinh.domain.model.UserType
import com.xinh.domain.model.UserType.Email
import com.xinh.domain.repository.UserRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class LoginTest : UnitTest() {

    private lateinit var login: Login

    @Mock
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        login = Login(schedulerProvider, userRepository)
    }

    @Test
    fun `login type email`() {
        login.buildObservable(Login.Params("email", "123", Email))

        verify(userRepository).login("email", "123")
    }

    @Test
    fun `login type facebook`() {
        login.buildObservable(Login.Params(type = UserType.Facebook))

        verify(userRepository).loginFacebook()
    }

    @Test
    fun `login type google`() {
        login.buildObservable(Login.Params(type = UserType.Google))

        verify(userRepository).loginGoogle()
    }

}