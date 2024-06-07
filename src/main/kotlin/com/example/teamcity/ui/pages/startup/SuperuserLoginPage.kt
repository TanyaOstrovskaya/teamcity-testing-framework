package com.example.teamcity.ui.pages.startup

import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.element
import com.example.teamcity.ui.Selectors.byId
import com.example.teamcity.ui.pages.Page
import com.example.teamcity.ui.pages.favourites.FavouritesPage
import java.time.Duration

class SuperuserLoginPage: Page() {

    private val passwordInput by lazy { element(byId("password")) }

    fun open(): SuperuserLoginPage {
        Selenide.open("/login.html?super=1")
        waitUntilPageLoaded()
        return this
    }

    fun loginAsSuperuser(password: String) {
        passwordInput
            .shouldBe(visible, Duration.ofSeconds(20))
            .sendKeys(password)
        submit()
        FavouritesPage().waitFavouriteBuildsHeader()
    }
}