package com.example.onlyofficetest.di

import com.example.onlyofficetest.presentation.login.LoginViewModel
import com.example.onlyofficetest.presentation.main.documents.DocumentsViewModel
import com.example.onlyofficetest.presentation.main.profile.ProfileViewModel
import com.example.onlyofficetest.presentation.main.rooms.RoomsViewModel
import com.example.onlyofficetest.presentation.main.trash.TrashViewModel
import dagger.Component

@Component(modules = [NetworkModule::class, SettingsModule::class])
interface AppComponent {
    fun inject(loginViewModel: LoginViewModel)
    fun inject(profileViewModel: ProfileViewModel)
    fun inject(roomsViewModel: RoomsViewModel)
    fun inject(trashViewModel: TrashViewModel)
    fun inject(documentsViewModel: DocumentsViewModel)
}