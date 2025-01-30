package com.example.onlyofficetest.domain.models

/**
 * Ответ от сервера на запрос авторизации
 *
 * @property response Внутренний объект [InnerAuthorizationResponse], содержащий токен авторизации
 */
data class AuthorizationResponse(
    val response: InnerAuthorizationResponse
)
