package com.example.onlyofficetest.presentation.main.rooms

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

class RoomsViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var remoteRepository: RemoteRepository

    init {
        (application as MyApp).appComponent.inject(this)
    }

    private val _rooms: MutableLiveData<List<FileListItem>> = MutableLiveData()
    val rooms: LiveData<List<FileListItem>>
        get() = _rooms

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable>
        get() = _error

    fun loadRooms() {
        viewModelScope.launch {
            try {
                val rooms = remoteRepository.getRooms()
                _rooms.value = rooms
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e
            }
        }
    }
}