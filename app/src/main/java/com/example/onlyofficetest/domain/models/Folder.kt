package com.example.onlyofficetest.domain.models

/**
 * Класс, представляющий собой запись о папке в файловой системе
 *
 * @property title Имя папки
 * @property id ID папки для получения вложенных папок с сервера
 */
data class Folder(override val title: String, val id: String): FileListItem()
