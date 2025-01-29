package com.example.onlyofficetest.di

import android.content.Context
import android.content.SharedPreferences
import com.example.onlyofficetest.data.repositories.SharedPrefsUserDataRepositoryImpl
import com.example.onlyofficetest.domain.repositories.UserDataRepository
import dagger.Module
import dagger.Provides

/**
 * Модуль, отвечающий за предоставление зависимостей для пользовательских настроек
 * и других служебных данных, хранящихся в SharedPreferences.
 *
 * Этот модуль включает в себя зависимости для работы с настройками пользователя
 * и данными пользователя, а также для управления учетной записью.
 *
 * @property context Контекст приложения, используемый для инициализации SharedPreferences
 */
@Module
class SettingsModule(private val context: Context) {
    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    fun provideUserDataRepository(sharedPreferences: SharedPreferences): UserDataRepository {
        return SharedPrefsUserDataRepositoryImpl(sharedPreferences)
    }
}