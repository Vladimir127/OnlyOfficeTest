package com.example.onlyofficetest.di

import com.example.onlyofficetest.presentation.login.LoginViewModel
import com.example.onlyofficetest.presentation.main.documents.DocumentsViewModel
import com.example.onlyofficetest.presentation.main.profile.ProfileViewModel
import com.example.onlyofficetest.presentation.main.rooms.RoomsViewModel
import com.example.onlyofficetest.presentation.main.trash.TrashViewModel
import dagger.Component

/**
 * Основной компонент зависимостей приложения.
 *
 * Этот интерфейс отвечает за предоставление зависимостей для различных
 * частей приложения, включая ViewModel, фрагменты и другие классы.
 * Компонент использует Dagger для внедрения зависимостей и использует
 * несколько модулей для конфигурации необходимых зависимостей.
 *
 * Модули, подключенные к этому компоненту:
 * - [NetworkModule]: предоставляет зависимости для сетевых операций.
 * - [SettingsModule]: управляет настройками приложения.
 */
@Component(modules = [NetworkModule::class, SettingsModule::class])
interface AppComponent {
    fun inject(loginViewModel: LoginViewModel)
    fun inject(profileViewModel: ProfileViewModel)
    fun inject(roomsViewModel: RoomsViewModel)
    fun inject(trashViewModel: TrashViewModel)
    fun inject(documentsViewModel: DocumentsViewModel)
}