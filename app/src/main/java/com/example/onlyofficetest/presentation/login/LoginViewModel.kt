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

    private var portal: String = ""
    private var email: String = ""
    private var password: String = ""

    private val _portalValid = MutableLiveData<Boolean>()
    val portalValid: LiveData<Boolean> = _portalValid

    private val _emailValid = MutableLiveData<Boolean>()
    val emailValid: LiveData<Boolean> = _emailValid

    private val _passwordValid = MutableLiveData<Boolean>()
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

    fun validatePortal(portal: String) {
        this.portal = portal
        _portalValid.value = Patterns.WEB_URL.matcher(portal).matches()
        checkFieldsValidity()
    }

    fun validateEmail(email: String) {
        this.email = email
        _emailValid.value = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        checkFieldsValidity()
    }

    fun validatePassword(password: String) {
        this.password = password
        _passwordValid.value = true
        checkFieldsValidity()
    }

    private fun checkFieldsValidity() {
        val isPortalValid = portal.isNotEmpty() && _portalValid.value ?: false
        val isEmailValid = email.isNotEmpty() && _emailValid.value ?: false
        val isPasswordValid = password.isNotEmpty() && _passwordValid.value ?: false
        _loginButtonEnabled.value = isPortalValid && isEmailValid && isPasswordValid
    }

    /**
     * Выполняет проверку, авторизован ли пользователь
     * Результат обновляет [isAuthorized]
     */
    fun checkIfIsAuthorized() {
        viewModelScope.launch {
            val isAuthorized = userDataRepository.getAuthorizedWithToken()
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