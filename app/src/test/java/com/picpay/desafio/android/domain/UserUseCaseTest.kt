package com.picpay.desafio.android.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.data.UserDummy
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.domain.usecase.UserUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserUseCaseTest {

    private val userRepository = mock<UserRepository>()

    private val userUseCase = UserUseCase(userRepository)

    @Test
    fun `should return a list of users successfully`() = runBlocking {
        // given
        whenever(userRepository.getUsers()).thenReturn(UserDummy.dummyListUser)

        // when
        val result = userUseCase()

        // then
        assert(result.isSuccess)
    }

    @Test
    fun `should return failure when repository throws exception`() = runBlocking {
        whenever(userRepository.getUsers()).thenThrow(NullPointerException())

        val result = userUseCase()

        assert(result.isFailure)
    }
}
