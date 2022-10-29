package com.picpay.desafio.android

import com.picpay.desafio.android.data.api.UserApi
import com.picpay.desafio.android.data.model.UserResponse

class ExampleService(
    private val service: UserApi
) {

    fun example(): List<UserResponse> {
        val users = service.getUsers().execute()

        return users.body() ?: emptyList()
    }
}