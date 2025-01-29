package com.example.onlyofficetest.domain.repositories

import com.example.onlyofficetest.domain.models.AuthorizationResponse

interface RemoteRepository {
    suspend fun authorize(portal: String, email: String, password: String): AuthorizationResponse
}