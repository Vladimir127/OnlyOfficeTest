package com.example.onlyofficetest.presentation.main.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlyofficetest.domain.models.UserProfile
import com.example.onlyofficetest.domain.repositories.RemoteRepository
import com.example.onlyofficetest.domain.repositories.UserDataRepository
import com.example.onlyofficetest.infrastructure.MyApp
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var remoteRepository: RemoteRepository

    @Inject
    lateinit var userDataRepository: UserDataRepository

    private val _userData = MutableLiveData<UserProfile>()
    val userData: LiveData<UserProfile> = _userData

    private val _loggedOut = MutableLiveData<Boolean>()
    val loggedOut: LiveData<Boolean> = _loggedOut

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable> = _error

    init {
        (application as MyApp).appComponent.inject(this)
    }

    fun loadData() {
        viewModelScope.launch {
            try {
                val result = remoteRepository.getProfileData()
                _userData.value = result
            } catch (e: Exception) {
                _error.value = e
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            remoteRepository.logout()
            userDataRepository.saveAuthorizedWithToken(false)
            _loggedOut.value = true
        }
    }
}