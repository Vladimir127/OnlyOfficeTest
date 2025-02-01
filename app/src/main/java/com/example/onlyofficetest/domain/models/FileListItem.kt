package com.example.onlyofficetest.domain.models

/**
 * Базовый абстрактный класс, представляющий собой запись о файле (документе) или папке,
 * для отображения записей этих двух типов в общем списке
 */
abstract class FileListItem {
    abstract val title: String
}