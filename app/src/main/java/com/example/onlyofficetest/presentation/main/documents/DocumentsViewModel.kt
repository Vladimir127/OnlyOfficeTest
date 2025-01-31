package com.example.onlyofficetest.presentation.main.documents

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlyofficetest.domain.models.FileListItem
import com.example.onlyofficetest.domain.models.Folder
import com.example.onlyofficetest.domain.repositories.RemoteRepository
import com.example.onlyofficetest.infrastructure.MyApp
import kotlinx.coroutines.launch
import javax.inject.Inject

class DocumentsViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var remoteRepository: RemoteRepository

    /** Стек навигации по папкам для отображения вложенных папок в одном и том же фрагменте и адаптере */
    private val folderStack = mutableListOf<Folder>()

    init {
        (application as MyApp).appComponent.inject(this)
    }

    private val _documents: MutableLiveData<List<FileListItem>> = MutableLiveData()
    val documents: LiveData<List<FileListItem>> = _documents

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable> = _error

    fun onFolderSelected(folder: Folder) {
        // Добавляем выбранную папку в стек
        folderStack.add(folder)

        // Получить содержимое папки
        loadDocuments(folder.id)
    }

    fun goBack(): Boolean {
        if (folderStack.isNotEmpty()) {
            // Убираем текущую папку
            folderStack.removeAt(folderStack.size - 1)

            // Получаем предыдущую папку
            val previousFolder = folderStack.lastOrNull()
            if (previousFolder != null) {
                // Загружаем содержимое предыдущей папки
                loadDocuments(previousFolder.id)
            } else {
                // Загрузка корневой папки
                loadDocuments()
            }
            return true
        }
        return false
    }

    fun loadDocuments() {
        viewModelScope.launch {
            try {
                val documents = remoteRepository.getDocuments()
                _documents.value = documents
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e
            }
        }
    }

    private fun loadDocuments(folderId: String) {
        viewModelScope.launch {
            try {
                val documents = remoteRepository.getDocumentsInFolder(folderId)
                _documents.value = documents
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e
            }
        }
    }
}