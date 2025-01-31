package com.example.onlyofficetest.domain.repositories

import com.example.onlyofficetest.domain.models.AuthorizationResponse
import com.example.onlyofficetest.domain.models.FileListItem
import com.example.onlyofficetest.domain.models.UserProfile

interface RemoteRepository {
    suspend fun authorize(portal: String, email: String, password: String): AuthorizationResponse

    suspend fun getProfileData(): UserProfile

    suspend fun getDocuments(): List<FileListItem>

    suspend fun getDocumentsInFolder(folderId: String): List<FileListItem>

    suspend fun getRooms(): List<FileListItem>

    suspend fun getTrash(): List<FileListItem>

    suspend fun logout()
}