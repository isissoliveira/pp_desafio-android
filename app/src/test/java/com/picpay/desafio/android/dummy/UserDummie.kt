package com.picpay.desafio.android.data

import com.picpay.desafio.android.data.mapper.toDomain
import com.picpay.desafio.android.data.model.UserResponse
import com.picpay.desafio.android.domain.mapper.toPresenter


internal object UserDummy {

    private val dummyUserResponse1 = UserResponse(
        img = "www.dummy-url1.com",
        name = "Dummy Name1",
        id = 0,
        username = "dummy1"
    )
    private val dummyUserResponse2 = UserResponse(
        img = "www.dummy-url2.com",
        name = "Dummy Name2",
        id = 0,
        username = "dummy2"
    )

    val dummyListUserResponse = listOf(dummyUserResponse1, dummyUserResponse2)

    val dummyListUser = dummyListUserResponse.toDomain()

    val dummyListUserVO = dummyListUser.toPresenter()
}