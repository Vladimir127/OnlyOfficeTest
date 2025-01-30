package com.example.onlyofficetest.data.retrofit

import com.example.onlyofficetest.domain.repositories.UserDataRepository

/**
 * Вспомогательный класс для управления токенами авторизации.
 *
 * Этот класс предоставляет методы для получения и сохранения
 * токенов доступа и обновления, взаимодействуя с [UserDataRepository].
 *
 * @property userDataRepository Репозиторий для хранения данных пользователя,
 * включая токены доступа и обновления.
 */
class TokenProvider(private val userDataRepository: UserDataRepository) {
    fun getAuthToken(): String {
        return userDataRepository.getAccessToken()
    }

    fun setAuthToken(token: String) {
        userDataRepository.saveAccessToken(token)
    }
}