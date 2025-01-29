package com.example.onlyofficetest.presentation.login

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlyofficetest.domain.repositories.RemoteRepository
import com.example.onlyofficetest.domain.repositories.UserDataRepository
import com.example.onlyofficetest.infrastructure.MyApp
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var remoteRepository: RemoteRepository

    @Inject
    lateinit var userDataRepository: UserDataRepository

    private val _portalValid = MutableLiveData<Boolean>(true)
    val portalValid: LiveData<Boolean> = _portalValid

    private val _emailValid = MutableLiveData<Boolean>(true)
    val emailValid: LiveData<Boolean> = _emailValid

    private val _passwordValid = MutableLiveData<Boolean>(true)
    val passwordValid: LiveData<Boolean> = _passwordValid

    private val _loginButtonEnabled = MutableLiveData<Boolean>().apply { value = false }
    val loginButtonEnabled: LiveData<Boolean> = _loginButtonEnabled

    private val _isAuthorized = MutableLiveData<Boolean>()
    val isAuthorized: LiveData<Boolean> = _isAuthorized

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable> = _error

    init {
        (application as MyApp).appComponent.inject(this)
    }

    fun validatePortal(name: String) {
        _portalValid.value = name.isNotEmpty() && Patterns.WEB_URL.matcher(name).matches()
        checkFieldsValidity()
    }

    fun validateEmail(name: String) {
        _emailValid.value = name.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(name).matches()
        checkFieldsValidity()
    }

    fun validatePassword(name: String) {
        _passwordValid.value = name.isNotEmpty()
        checkFieldsValidity()
    }

    private fun checkFieldsValidity() {
        val isPortalValid = _portalValid.value ?: false
        val isEmailValid = _emailValid.value ?: false
        val isPasswordValid = _passwordValid.value ?: false
        _loginButtonEnabled.value = isPortalValid && isEmailValid && isPasswordValid
    }

    /**
     * Выполняет проверку, авторизован ли пользователь
     * Результат обновляет [isAuthorized]
     */
    fun checkIfIsAuthorized() {
        viewModelScope.launch {
            val token = userDataRepository.getAccessToken()
            val isAuthorized = token.isNotEmpty()
            _isAuthorized.value = isAuthorized
        }
    }

    fun authorize(portal: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val result = remoteRepository.authorize(portal, email, password)
                userDataRepository.saveAccessToken(result.response.token)
                userDataRepository.saveAuthorizedWithToken(true)
                _isAuthorized.value = true
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e
            }
        }
    }
}