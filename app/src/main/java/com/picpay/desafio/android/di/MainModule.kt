package com.picpay.desafio.android.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.BuildConfig
import com.picpay.desafio.android.data.api.UserApi
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.domain.usecase.UserUseCase
import com.picpay.desafio.android.presentation.viewModel.UserViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val mainModule = module {

    single<Gson> {
        GsonBuilder().create()
    }

 /*   single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }*/

    single {
        /*val cacheDir = File((get<Context>() as MyApp).cacheDir, "http")
        val cache = Cache(
            cacheDir,
            10 * 1024 * 1024 // 10 MB
        )*/

        OkHttpClient.Builder()
            //.cache(cache)
            //.addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BuildConfig.URL_API)
            .addConverterFactory(GsonConverterFactory.create(get()))
            //.addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    single {
        get<Retrofit>().create(UserApi::class.java)
    }
    single {
        UserRepository(get())
    }
    single {
        UserUseCase(get())
    }
    
    viewModel { UserViewModel(get()) }
    
}