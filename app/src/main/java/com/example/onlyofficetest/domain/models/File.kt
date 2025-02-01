package com.example.onlyofficetest.domain.models

/**
 * Класс, представляющий собой запись о файле (документе) в файловой системе
 *
 * @property title Имя файла
 */
data class File(override val title: String): FileListItem()
