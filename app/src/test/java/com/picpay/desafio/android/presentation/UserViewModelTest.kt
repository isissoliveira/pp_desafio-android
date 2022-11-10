package com.picpay.desafio.android.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.data.UserDummy
import com.picpay.desafio.android.data.api.UserApi
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.domain.usecase.UserUseCase
import com.picpay.desafio.android.presentation.model.UserVO
import com.picpay.desafio.android.presentation.viewModel.UserViewModel
import io.mockk.*
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

    private val userUseCase: UserUseCase = mockk()
    private val lifecycleRegistry = LifecycleRegistry(mockk())

    private lateinit var userViewModel: UserViewModel

    @Before
    fun before() {
        userViewModel = UserViewModel(userUseCase)
    }

    @Test
    fun `should return a list of users successfully`() = runBlocking {

        coEvery {
            userUseCase()
        } returns Result.success(UserDummy.dummyListUserVO)

        val observer: Observer<UserViewModel.UserState> = mockk()
        every { observer.onChanged(any()) } just Runs

        userViewModel.usersListState.observe(
            { lifecycleRegistry },
            observer
        )

        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        userViewModel.getUsers()

        verifySequence {
            observer.onChanged(UserViewModel.UserState.Loading)
            observer.onChanged(UserViewModel.UserState.Success(UserDummy.dummyListUserVO))
        }
        confirmVerified(observer)
    }

    @Test
    fun `should return an empty list of users successfully`() = runBlocking {

        coEvery {
            userUseCase()
        } returns Result.success(listOf())

        val observer: Observer<UserViewModel.UserState> = mockk()
        every { observer.onChanged(any()) } just Runs

        userViewModel.usersListState.observe(
            { lifecycleRegistry },
            observer
        )

        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        userViewModel.getUsers()

        verifySequence {
            observer.onChanged(UserViewModel.UserState.Loading)
            observer.onChanged(UserViewModel.UserState.Empty)
        }
        confirmVerified(observer)
    }

    @Test
    fun `should return failure when userUseCase returns failure`() = runBlocking {

        coEvery {
            userUseCase()
        } returns Result.failure(Exception())

        val observer: Observer<UserViewModel.UserState> = mockk()
        every { observer.onChanged(any()) } just Runs

        userViewModel.usersListState.observe(
            { lifecycleRegistry },
            observer
        )

        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        userViewModel.getUsers()

        verifySequence {
            observer.onChanged(UserViewModel.UserState.Loading)
            observer.onChanged(UserViewModel.UserState.Failure)
        }
        confirmVerified(observer)
    }

}
