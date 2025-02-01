package com.example.onlyofficetest.data.retrofit

import com.example.onlyofficetest.domain.models.AuthRequestBody
import com.example.onlyofficetest.domain.models.AuthorizationResponse
import com.example.onlyofficetest.domain.models.FilesResponse
import com.example.onlyofficetest.domain.models.ProfileResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * Интерфейс для доступа к эндпоинтам сервера с помощью Retrofit
 */
interface PortalService {
    @POST
    suspend fun getToken(@Url url: String, @Body requestBody: AuthRequestBody): AuthorizationResponse

    @GET
    suspend fun getProfileData(@Url url: String): ProfileResponse

    @GET
    suspend fun getDocuments(@Url url: String): FilesResponse

    @GET
    suspend fun getDocumentsInFolder(@Url url: String): FilesResponse

    @GET
    suspend fun getRooms(@Url url: String): FilesResponse

    @GET
    suspend fun getTrash(@Url url: String): FilesResponse

    @POST
    suspend fun logout(@Url url: String)
}