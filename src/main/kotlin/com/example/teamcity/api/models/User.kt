package com.example.teamcity.api.models


data class User (
    var username: String,
    var password: String,
    var email: String? =  null,
    var roles: Roles? = null
) {
}