package com.example.onlyofficetest.presentation.main.trash

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

class TrashViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var remoteRepository: RemoteRepository

    init {
        (application as MyApp).appComponent.inject(this)
    }

    private val _trash: MutableLiveData<List<FileListItem>> = MutableLiveData()
    val trash: LiveData<List<FileListItem>> = _trash

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable> = _error

    fun loadTrash() {
        viewModelScope.launch {
            try {
                val trash = remoteRepository.getTrash()
                _trash.value = trash
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e
            }
        }
    }
}