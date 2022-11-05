package com.picpay.desafio.android.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.data.UserDummy
import com.picpay.desafio.android.data.api.UserApi
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.domain.usecase.UserUseCase
import com.picpay.desafio.android.presentation.viewModel.UserViewModel
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class UserViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val userUseCase = mock<UserUseCase>()
    private val userUseCase2 : UserUseCase = mockk()

    private lateinit var userViewModel: UserViewModel

    private val observer = mock<Observer<UserViewModel.UserState>>()

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        userViewModel = UserViewModel(userUseCase)
        userViewModel.usersListState.observeForever(observer)
    }

    @Test
    fun `should return a list of users successfully`() = runBlocking {
        // given
        whenever(userUseCase()).thenReturn(Result.success(UserDummy.dummyListUserVO))

        //val observer = mock<Observer<UserViewModel.UserState>>()

        // when
        userViewModel.getUsers()
        val t = userViewModel.usersListState
        // then
        verify(observer).onChanged(UserViewModel.UserState.Success(UserDummy.dummyListUserVO))
    }

}
