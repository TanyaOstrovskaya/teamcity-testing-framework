package com.example.teamcity.ui.pages.favourites

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Selenide.element
import com.example.teamcity.ui.Selectors.byText
import com.example.teamcity.ui.pages.Page
import java.time.Duration

open class FavouritesPage: Page() {

    private val favouriteBuildsHeader by lazy { element(byText("Favorite Builds")) }

    fun waitUntilFavouritePageLoaded() {
        waitUntilPageLoaded()
        header
            .shouldBe(Condition.visible, Duration.ofSeconds(10))
            .has(text("All Projects"))
    }

    fun waitFavouriteBuildsHeader() {
        waitUntilPageLoaded()
        favouriteBuildsHeader
            .shouldBe(Condition.visible, Duration.ofSeconds(30))

    }
}
