package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.domain.mapper.toPresenter
import com.picpay.desafio.android.presentation.model.UserVO

class UserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Result<List<UserVO>?> {
        return try {
            val listUserVO = userRepository.getUsers()?.toPresenter()
            Result.success(listUserVO)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
