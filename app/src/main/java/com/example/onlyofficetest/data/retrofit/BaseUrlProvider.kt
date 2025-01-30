package com.example.onlyofficetest.data.retrofit

import com.example.onlyofficetest.domain.repositories.UserDataRepository

/**
 * Класс для сохранения адреса портала при авторизации
 * и его получения при последующих запросах
 *
 * @property userDataRepository
 */
class BaseUrlProvider(val userDataRepository: UserDataRepository) {
    fun setBaseUrl(url: String) {
        userDataRepository.saveBaseUrl(url)
    }

    fun getBaseUrl(): String {
        return userDataRepository.getBaseUrl()
    }
}