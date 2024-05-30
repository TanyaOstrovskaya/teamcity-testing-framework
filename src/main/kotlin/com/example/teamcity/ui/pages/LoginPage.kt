package com.example.teamcity.ui.pages

import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.element
import com.example.teamcity.api.models.User
import com.example.teamcity.ui.Selectors.byId
import java.time.Duration

private const val LOGIN_PAGE_URL = "/login.html"

class LoginPage: Page() {
    private val usernameInput = element(byId("username"))
    private val passwordInput = element(byId("password"))

    fun open(): LoginPage {
        Selenide.open(LOGIN_PAGE_URL)
        return this
    }

    fun login(user: User) {
        usernameInput.shouldBe(visible, Duration.ofSeconds(30)).sendKeys(user.username)
        passwordInput.shouldBe(visible, Duration.ofSeconds(30)).sendKeys(user.password)
        submit()
        waitRingLoaderAbsent()
    }
}