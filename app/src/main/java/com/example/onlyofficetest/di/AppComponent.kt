package com.example.onlyofficetest.di

import com.example.onlyofficetest.presentation.login.LoginViewModel
import dagger.Component

@Component(modules = [NetworkModule::class, SettingsModule::class])
interface AppComponent {
    fun inject(loginViewModel: LoginViewModel)
}