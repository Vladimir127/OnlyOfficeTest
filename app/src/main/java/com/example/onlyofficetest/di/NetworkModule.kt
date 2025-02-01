package com.example.onlyofficetest.di

import com.example.onlyofficetest.data.repositories.WebRemoteRepositoryImpl
import com.example.onlyofficetest.data.retrofit.AuthInterceptor
import com.example.onlyofficetest.data.retrofit.BaseUrlProvider
import com.example.onlyofficetest.data.retrofit.PortalService
import com.example.onlyofficetest.data.retrofit.TokenProvider
import com.example.onlyofficetest.domain.repositories.RemoteRepository
import com.example.onlyofficetest.domain.repositories.UserDataRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://testdocspaceportal.onlyoffice.com/"

/**
 * Модуль, отвечающий за предоставление зависимостей для работы с сетью с помощью Retrofit
 */
@Module
class NetworkModule {
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    fun provideTokenProvider(userDataRepository: UserDataRepository): TokenProvider {
        return TokenProvider(userDataRepository)
    }

    @Provides
    fun provideOkHttpClient(
        tokenProvider: TokenProvider
    ): OkHttpClient {
        val authInterceptor = AuthInterceptor(tokenProvider)

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideProductService(retrofit: Retrofit): PortalService {
        return retrofit.create(PortalService::class.java)
    }

    @Provides
    fun provideBaseUrlProvider(userDataRepository: UserDataRepository): BaseUrlProvider {
        return BaseUrlProvider(userDataRepository)
    }

    @Provides
    fun provideRemoteRepository(apiService: PortalService, baseUrlProvider: BaseUrlProvider): RemoteRepository {
        return WebRemoteRepositoryImpl(apiService, baseUrlProvider)
    }
}