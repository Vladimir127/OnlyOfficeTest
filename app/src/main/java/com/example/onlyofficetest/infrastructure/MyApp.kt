package com.example.onlyofficetest.infrastructure

import android.app.Application
import com.example.onlyofficetest.di.AppComponent
import com.example.onlyofficetest.di.DaggerAppComponent
import com.example.onlyofficetest.di.NetworkModule
import com.example.onlyofficetest.di.SettingsModule

class MyApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .settingsModule(SettingsModule(this))
            .build()
    }
}
