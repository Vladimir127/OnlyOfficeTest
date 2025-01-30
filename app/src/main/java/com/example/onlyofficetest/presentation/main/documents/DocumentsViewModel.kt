package com.example.onlyofficetest.presentation.main.documents

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlyofficetest.domain.models.FileListItem
import com.example.onlyofficetest.domain.repositories.RemoteRepository
import com.example.onlyofficetest.infrastructure.MyApp
import kotlinx.coroutines.launch
import javax.inject.Inject

class DocumentsViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var remoteRepository: RemoteRepository

    init {
        (application as MyApp).appComponent.inject(this)
    }

    private val _documents: MutableLiveData<List<FileListItem>> = MutableLiveData()
    val documents: LiveData<List<FileListItem>> = _documents

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable> = _error

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
}