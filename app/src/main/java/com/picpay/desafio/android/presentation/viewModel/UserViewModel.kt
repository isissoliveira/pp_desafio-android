package com.picpay.desafio.android.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.usecase.UserUseCase
import com.picpay.desafio.android.presentation.model.UserVO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(
    private val userUseCase: UserUseCase
) : ViewModel() {
    private val _usersListState = MutableLiveData<UserState>()
    val usersListState: LiveData<UserState>
        get() = _usersListState

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            userUseCase()
                .onSuccess { data ->
                    if(data.isEmpty()){
                        _usersListState.postValue(UserState.Empty)
                    }
                    else {
                        _usersListState.postValue(UserState.Success(data))
                    }
                }
                .onFailure {
                    _usersListState.postValue(UserState.Failure)
                }
        }
    }

    sealed class UserState{
        object Loading : UserState()
        object Failure : UserState()
        object Empty : UserState()
        data class Success(val listUsersVO: List<UserVO>) : UserState()
    }
}
