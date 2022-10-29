package com.picpay.desafio.android.data.api

import com.picpay.desafio.android.data.model.UserResponse
import retrofit2.http.GET

interface UserApi {
    @GET("users")
    suspend fun getUsers(): List<UserResponse>?
}
