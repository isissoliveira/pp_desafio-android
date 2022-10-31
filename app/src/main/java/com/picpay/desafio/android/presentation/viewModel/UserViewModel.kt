package com.picpay.desafio.android.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.usecase.UserUseCase
import com.picpay.desafio.android.presentation.model.UserVO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserViewModel(
    private val userUseCase: UserUseCase
) : ViewModel() {
    private val _usersListState = MutableLiveData<Result<List<UserVO>>>()
    val usersListState: LiveData<Result<List<UserVO>>>
        get() = _usersListState

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            val listUsers = userUseCase()
            _usersListState.postValue(listUsers)
        }
    }
}
