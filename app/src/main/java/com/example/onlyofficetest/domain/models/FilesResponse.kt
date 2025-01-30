package com.example.onlyofficetest.domain.models

/**
 * Ответ от сервера, содержащий списки файлов и папок.
 * Используется в разделах "Documents", "Rooms" и "Trash"
 */
data class FilesResponse(
    val response: InnerFilesResponse
)
