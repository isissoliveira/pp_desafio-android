package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.api.UserApi
import com.picpay.desafio.android.data.mapper.toDomain
import com.picpay.desafio.android.data.model.UserResponse
import com.picpay.desafio.android.domain.model.User
import timber.log.Timber

class UserRepository(private val userApi: UserApi) {
    suspend fun getUsers(): List<User>? {
        return try {
            val usersResponse: List<UserResponse>? = userApi.getUsers()
            usersResponse?.toDomain()
        } catch (e: Exception) {
            Timber.e("Couldn't get the users : ${e.message}")
            null
        }
    }
}
