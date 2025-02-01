package com.example.onlyofficetest.domain.models

/**
 * Тело запроса авторизации
 *
 * @property userName Имя пользователя (e-mail)
 * @property password Пароль
 */
data class AuthRequestBody(val userName: String, val password: String)
