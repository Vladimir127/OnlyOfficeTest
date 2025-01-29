package com.example.onlyofficetest.data.repositories

import com.example.onlyofficetest.data.retrofit.PortalService
import com.example.onlyofficetest.domain.models.AuthRequestBody
import com.example.onlyofficetest.domain.models.AuthorizationResponse
import com.example.onlyofficetest.domain.repositories.RemoteRepository

class WebRemoteRepositoryImpl(private val portalService: PortalService) : RemoteRepository {
    override suspend fun authorize(portal: String, email: String, password: String): AuthorizationResponse {
        val url = portal + "api/2.0/authentication"
        val requestBody = AuthRequestBody(email, password)
        return portalService.getToken(url, requestBody)
    }
}