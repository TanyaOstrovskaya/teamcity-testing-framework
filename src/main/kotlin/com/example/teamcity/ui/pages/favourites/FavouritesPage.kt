package com.example.teamcity.ui.pages.favourites

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Condition.text
import com.example.teamcity.ui.pages.Page
import java.time.Duration

open class FavouritesPage: Page() {

    fun waitUntilFavouritePageLoaded() {
        waitUntilPageLoaded()
        header
            .shouldBe(Condition.visible, Duration.ofSeconds(10))
            .has(text("All Projects"))
    }
}
