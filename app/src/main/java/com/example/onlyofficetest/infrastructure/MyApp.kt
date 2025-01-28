package com.example.onlyofficetest.infrastructure

import android.app.Application
import com.example.onlyofficetest.di.AppComponent
import com.example.onlyofficetest.di.DaggerAppComponent
import com.example.onlyofficetest.di.NetworkModule

class MyApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .build()
    }
}
