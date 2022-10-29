package com.picpay.desafio.android.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.domain.usecase.UserUseCase
import com.picpay.desafio.android.presentation.model.UserVO

class UserViewModel(
    private val userUseCase: UserUseCase
) : ViewModel() {
    private lateinit var _usersListState: MutableLiveData<Result<List<UserVO>?>>
    val usersListState: LiveData<Result<List<UserVO>?>>
        get() = _usersListState

    suspend fun getUsers() {
        val listUsers: Result<List<UserVO>?> = userUseCase()
        _usersListState.postValue(listUsers)
    }
}
