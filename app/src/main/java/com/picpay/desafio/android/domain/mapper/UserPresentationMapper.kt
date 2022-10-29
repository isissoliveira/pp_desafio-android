package com.picpay.desafio.android.domain.mapper

import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.presentation.model.UserVO

fun User.toPresenter() = UserVO(
    img = img,
    name = name,
    id = id,
    username = username,
    initials = initials
)

fun List<User>.toPresenter() = (
    this.map { user -> user.toPresenter() }
)
