package com.picpay.desafio.android.domain.model

import com.picpay.desafio.android.utils.extensions.getInitials

data class User(
    val img: String? = "",
    val name: String,
    val id: Int,
    val username: String,
    val initials: String = name.getInitials()
)
