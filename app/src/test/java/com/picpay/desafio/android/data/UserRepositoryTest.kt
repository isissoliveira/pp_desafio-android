package com.picpay.desafio.android.data

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.data.api.UserApi
import com.picpay.desafio.android.data.repository.UserRepository
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection

class UserRepositoryTest {

    private val userApi = mock<UserApi>()

    private val userRepository = UserRepository(userApi)

    @Test
    fun `should return a list of users successfully`() = runBlocking {
        // given
        whenever(userApi.getUsers()).thenReturn(UserDummy.dummyListUserResponse)
        val usersResponse = userApi.getUsers()
        val expectedUsers = UserDummy.dummyListUser

        // when
        val users = userRepository.getUsers()

        // then
        assertEquals(users, expectedUsers)
    }

    @Test(expected = Exception::class)
    fun `should throw an exception when api returns any error`() = runBlocking {
        whenever(userApi.getUsers()).thenThrow(
            HttpException(
                Response.error<String>(
                    HttpURLConnection.HTTP_BAD_REQUEST,
                    "".toResponseBody()
                )
            )
        )
        val users = userRepository.getUsers()
    }
}
