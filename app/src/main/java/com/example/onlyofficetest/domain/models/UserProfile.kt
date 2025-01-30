package com.example.onlyofficetest.domain.models

/**
 * Объект, содержащий данные профиля
 */
data class UserProfile(
    val firstName: String,
    val lastName: String,
    val email: String,
    var avatar: String
)
