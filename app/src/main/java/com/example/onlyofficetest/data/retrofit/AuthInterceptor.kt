package com.example.onlyofficetest.data.retrofit

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Интерцептор для добавления токена авторизации к каждому HTTP-запросу.
 *
 * Этот класс реализует интерфейс [Interceptor] и добавляет заголовок авторизации
 * к запросам, используя токен, предоставляемый [TokenProvider].
 *
 * @property tokenProvider Поставщик токена, используемый для получения токена авторизации.
 */
class AuthInterceptor(private val tokenProvider: TokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider.getAuthToken()

        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        requestBuilder.header("Authorization", "Bearer $token")

        return chain.proceed(requestBuilder.build())
    }
}