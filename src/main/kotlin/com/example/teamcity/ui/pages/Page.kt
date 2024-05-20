package com.example.teamcity.ui.pages

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.SelenideElement
import com.example.teamcity.ui.Selectors.byClass
import com.example.teamcity.ui.Selectors.byDataTest
import com.example.teamcity.ui.Selectors.byType
import com.example.teamcity.ui.elements.PageElement
import java.time.Duration
import java.util.function.Function

open class Page {

    private val submitButton = element(byType("submit"))
    private val savingWaitingMarker = element(byType("saving"))
    private val pageWaitingMarker = element(byDataTest("ring-loader"))
    private val ringLoader = element(byClass("progressRing"))


    fun submit() {
        submitButton.click()
        waitUntilDataSaved()
    }

    fun waitRingLoaderAbsent() {
        ringLoader.shouldNotBe(visible, Duration.ofSeconds(30))
    }

    fun waitUntilPageLoaded() {
        pageWaitingMarker.shouldNotBe(visible, Duration.ofMinutes(1))
    }

    fun waitUntilDataSaved() {
        savingWaitingMarker.shouldBe(Condition.not(visible), Duration.ofSeconds(30))
    }

    fun <T: PageElement> generatePageElements(
        collection: ElementsCollection,
        creator: (SelenideElement) -> T ): List<T> {
        collection.forEach { it.shouldBe(visible, Duration.ofSeconds(30)) }
        return collection.map { creator(it) }
    }

}