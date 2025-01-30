package com.example.onlyofficetest.data.repositories

import com.example.onlyofficetest.data.retrofit.BaseUrlProvider
import com.example.onlyofficetest.data.retrofit.PortalService
import com.example.onlyofficetest.domain.models.AuthRequestBody
import com.example.onlyofficetest.domain.models.AuthorizationResponse
import com.example.onlyofficetest.domain.models.File
import com.example.onlyofficetest.domain.models.FileListItem
import com.example.onlyofficetest.domain.models.FilesResponse
import com.example.onlyofficetest.domain.models.Folder
import com.example.onlyofficetest.domain.models.UserProfile
import com.example.onlyofficetest.domain.repositories.RemoteRepository

class WebRemoteRepositoryImpl(private val portalService: PortalService, private val baseUrlProvider: BaseUrlProvider) : RemoteRepository {
    override suspend fun authorize(portal: String, email: String, password: String): AuthorizationResponse {
        baseUrlProvider.setBaseUrl(portal)

        val url = portal + "api/2.0/authentication"
        val requestBody = AuthRequestBody(email, password)
        return portalService.getToken(url, requestBody)
    }

    override suspend fun getProfileData(): UserProfile {
        val baseUrl = baseUrlProvider.getBaseUrl()
        val url = baseUrl + "api/2.0/people/@self"
        return portalService.getProfileData(url).response.apply { avatar = baseUrl + avatar }
    }

    override suspend fun getDocuments(): List<FileListItem> {
        val baseUrl = baseUrlProvider.getBaseUrl()
        val url = baseUrl + "api/2.0/files/@my"
        val response = portalService.getDocuments(url).response
        val items = mergeFilesAndFolders(response.files, response.folders)
        return items
    }

    override suspend fun getRooms(): List<FileListItem> {
        val baseUrl = baseUrlProvider.getBaseUrl()
        val url = baseUrl + "api/2.0/files/rooms"
        val response = portalService.getRooms(url).response
        val items = mergeFilesAndFolders(response.files, response.folders)
        return items
    }

    override suspend fun getTrash(): List<FileListItem> {
        val baseUrl = baseUrlProvider.getBaseUrl()
        val url = baseUrl + "api/2.0/files/@trash"
        val response = portalService.getTrash(url).response
        val items = mergeFilesAndFolders(response.files, response.folders)
        return items
    }

    override suspend fun logout() {
        val baseUrl = baseUrlProvider.getBaseUrl()
        val url = baseUrl + "api/2.0/authentication/logout"
        portalService.logout(url)
    }

    fun mergeFilesAndFolders(files: List<File>, folders: List<Folder>): List<FileListItem> {
        val items = mutableListOf<FileListItem>()
        items.addAll(folders)
        items.addAll(files)
        return items
    }
}