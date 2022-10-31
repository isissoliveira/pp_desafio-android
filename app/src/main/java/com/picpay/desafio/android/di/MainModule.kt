package com.picpay.desafio.android.di

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.BuildConfig
import com.picpay.desafio.android.data.api.UserApi
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.domain.usecase.UserUseCase
import com.picpay.desafio.android.presentation.viewModel.UserViewModel
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CACHE_SIZE = 10 * 1024 * 1024L // 10 MB
const val CACHE_CONTROL_HEADER = "Cache-Control"

val mainModule = module {

    single<Gson> {
        GsonBuilder().create()
    }
    single {
        httpCache(this.androidApplication())
    }

    single {
        OkHttpClient.Builder()
            .cache(get())
            .addNetworkInterceptor(CacheInterceptor())
            .addInterceptor(loggingInterceptor())
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BuildConfig.URL_API)
            .addConverterFactory(GsonConverterFactory.create(get()))
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

private fun httpCache(application: Application): Cache {
    return Cache(application.applicationContext.cacheDir, CACHE_SIZE)
}

private fun loggingInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.HEADERS
}

class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalResponse = chain.proceed(request)

        val cacheControl = CacheControl.Builder()
            .maxAge(3, TimeUnit.MINUTES)
            .build()

        return originalResponse.newBuilder()
            .header(CACHE_CONTROL_HEADER, cacheControl.toString())
            .build()
    }
}
