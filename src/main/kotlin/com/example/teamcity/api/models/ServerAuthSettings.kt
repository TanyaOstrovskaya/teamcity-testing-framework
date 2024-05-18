package com.example.teamcity.api.models

data class ServerAuthSettings (
    val modules: AuthSettingsModules? = null,
    val allowGuest: String? = null,
    val collapseLoginForm: String? = null,
    val emailVerification: String? = null,
    val guestUsername: String? = null,
    val perProjectPermissions: String? = null,
    val welcomeText: String? = null
)

data class AuthSettingsModules (
    val module: List<AuthSettingsModule>? = null
)

data class AuthSettingsModule (
    val properties: AuthSettingsProperties? = null,
    val name: String? = null
)

data class AuthSettingsProperties (
    val property: List<AuthSettingsProperty>? = null,
    val count: String? = null
)

data class AuthSettingsProperty (
    val name: String? = null,
    val value: String? = null
)