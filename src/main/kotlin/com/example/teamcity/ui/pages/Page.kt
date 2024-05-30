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

open class Page {

    protected val header  by lazy { element(byClass("ProjectPageHeader__title--ih")) }
    private val submitButton by lazy {  element(byType("submit")) }
    private val savingWaitingMarker by lazy {  element(byType("saving")) }
    private val createProgressMarker by lazy {  element(byType("createProgress")) }
    private val pageWaitingMarker by lazy {  element(byDataTest("ring-loader")) }
    private val ringLoader by lazy {  element(byClass("progressRing")) }

    fun submit() {
        submitButton.click()
        waitUntilDataSaved()
    }

    fun waitCreateLoaderAbsent() {
        createProgressMarker.shouldNotBe(visible, Duration.ofSeconds(30))
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
