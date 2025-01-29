package com.example.onlyofficetest.data.retrofit

import com.example.onlyofficetest.domain.models.AuthRequestBody
import com.example.onlyofficetest.domain.models.AuthorizationResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface PortalService {
    /**
     * Возвращает токен авторизации
     *
     * @param requestBody Тело запроса, содержащее данные для аутентификации
     * @return Ответ с токеном авторизации
     */
    @POST
    suspend fun getToken(@Url url: String, @Body requestBody: AuthRequestBody): AuthorizationResponse
}