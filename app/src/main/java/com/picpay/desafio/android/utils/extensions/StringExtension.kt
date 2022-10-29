package com.picpay.desafio.android.utils.extensions

fun String.getInitials(): String {
    val names = this.split(" ")
    val string1 = names.firstOrNull().orEmpty()
    val string2 = names.lastOrNull().orEmpty()
    return string1 + string2
}

