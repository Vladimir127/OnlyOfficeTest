package com.example.onlyofficetest.di

import com.example.onlyofficetest.data.repositories.WebRemoteRepositoryImpl
import com.example.onlyofficetest.data.retrofit.PortalService
import com.example.onlyofficetest.domain.repositories.RemoteRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://testdocspaceportal.onlyoffice.com/"

@Module
class NetworkModule {
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideProductService(retrofit: Retrofit): PortalService {
        return retrofit.create(PortalService::class.java)
    }

    @Provides
    fun provideRemoteRepository(apiService: PortalService): RemoteRepository {
        return WebRemoteRepositoryImpl(apiService)
    }
}