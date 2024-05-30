package com.example.teamcity.ui.pages

import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.element
import com.example.teamcity.api.models.User
import com.example.teamcity.ui.Selectors.byId

private const val LOGIN_PAGE_URL = "/login.html"

class LoginPage: Page() {
    private val usernameInput = element(byId("username"))
    private val passwordInput = element(byId("password"))

    fun open(): LoginPage {
        Selenide.open(LOGIN_PAGE_URL)
        return this
    }

    fun login(user: User) {
        usernameInput.sendKeys(user.username)
        passwordInput.sendKeys(user.password)
        submit()
    }
}