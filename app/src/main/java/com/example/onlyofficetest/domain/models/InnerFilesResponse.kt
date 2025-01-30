package com.example.onlyofficetest.domain.models

/**
 * Внутренний объект response, содержащий списки файлов и папок
 * Используется в разделах "Documents", "Rooms" и "Trash"
 */
data class InnerFilesResponse(
    val folders: List<Folder>,
    val files: List<File>
)
