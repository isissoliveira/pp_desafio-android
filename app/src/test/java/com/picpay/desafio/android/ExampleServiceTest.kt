package com.picpay.desafio.android

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.data.api.UserApi
import com.picpay.desafio.android.data.model.UserResponse
import junit.framework.Assert.assertEquals
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class ExampleServiceTest {

    private val api = mock<UserApi>()

    private val service = ExampleService(api)

    /*@Test
    fun exampleTest() {
        // given
        val call = mock<Call<List<UserResponse>>>()
        val expectedUsers = emptyList<UserResponse>()

        whenever(call.execute()).thenReturn(Response.success(expectedUsers))
        whenever(api.getUsers()).thenReturn(call)

        // when
        val users = service.example()

        // then
        assertEquals(users, expectedUsers)
    }*/
}