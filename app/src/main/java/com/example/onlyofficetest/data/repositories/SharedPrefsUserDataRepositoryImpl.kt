package com.example.onlyofficetest.data.repositories

import android.content.SharedPreferences
import com.example.onlyofficetest.domain.repositories.UserDataRepository

/**
 * Реализация репозитория для хранения данных пользователя, работающая с помощью Shared Preferences
 *
 * @property sharedPreferences Экземпляр [SharedPreferences] чтения и записи значений
 */
class SharedPrefsUserDataRepositoryImpl(private val sharedPreferences: SharedPreferences) :
    UserDataRepository {

    override fun saveAccessToken(accessToken: String) {
        val editor = sharedPreferences.edit()
        editor.putString(TAG_ACCESS_TOKEN, accessToken)
        editor.apply()
    }

    override fun getAccessToken(): String {
        return sharedPreferences.getString(TAG_ACCESS_TOKEN, "") ?: ""
    }

    /**
     * Возвращает признак того, авторизован ли пользователь с помощью токена аутентификации.
     * Если пользователь не авторизован, при запуске приложения открывается экран авторизации.
     *
     * @return Признак того, авторизован ли пользователь с помощью токена аутентификации
     */
    override fun getAuthorizedWithToken(): Boolean {
        return sharedPreferences.getBoolean(AUTH_WITH_TOKEN, false)
    }

    /**
     * Сохраняет признак того, авторизован ли пользователь с помощью токена аутентификации.
     * Если пользователь не авторизован, при запуске приложения открывается экран авторизации.
     *
     * @param value Признак того, авторизован ли пользователь с помощью токена аутентификации
     */
    override fun saveAuthorizedWithToken(value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(AUTH_WITH_TOKEN, value)
        editor.apply()
    }

    companion object {
        /** Ключ для чтения и записи значения в методах [getAccessToken] и [saveAccessToken] */
        const val TAG_ACCESS_TOKEN = "access_token"

        /** Ключ для чтения и записи значения в методах [getAuthorizedWithToken] и [saveAuthorizedWithToken] */
        const val AUTH_WITH_TOKEN = "auth_with_token"
    }
}