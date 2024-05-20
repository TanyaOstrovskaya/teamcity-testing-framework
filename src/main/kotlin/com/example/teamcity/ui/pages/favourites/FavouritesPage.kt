package com.example.teamcity.ui.pages.favourites

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide.element
import com.example.teamcity.ui.Selectors.byClass
import com.example.teamcity.ui.pages.Page
import java.time.Duration

open class FavouritesPage: Page() {

    private val header = element(byClass("ProjectPageHeader__title--ih"))

    fun waitUntilFavouritePageLoaded() {
        waitUntilPageLoaded()
        header.shouldBe(Condition.visible, Duration.ofSeconds(10))
    }
}