package com.example.onlyofficetest.domain.models

/**
 * Внутренний объект response, находящийся в ответе от сервера на запрос авторизации и содержащий токен
 *
 * @property token Токен авторизации
 */
data class TokenResponse(
    val token: String
)
