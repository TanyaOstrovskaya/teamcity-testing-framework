package com.example.teamcity.ui.pages.startup

import com.codeborne.selenide.Condition.enabled
import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.element
import com.example.teamcity.ui.Selectors.byId
import com.example.teamcity.ui.pages.Page
import java.time.Duration

class StartUpPage: Page() {

    private val proceedButton  by lazy { element(byId("proceedButton")) }
    private val acceptLicense  by lazy { element(byId("accept")) }
    private val startUpHeader  by lazy { element(byId("header")) }

    fun open(): StartUpPage {
        Selenide.open("/")
        waitUntilPageLoaded()
        return this
    }

    fun setUpTeamcityServer(): StartUpPage {
        waitUntilPageLoaded()
        proceedButton.click()
        waitUntilPageLoaded()
        proceedButton.click()
        waitUntilPageLoaded()
        acceptLicense.shouldBe(enabled, Duration.ofMinutes(5))
        acceptLicense.scrollTo()
        acceptLicense.click()
        submitButton.click()

        return this
    }

    fun checkHeader(text: String): StartUpPage {
        startUpHeader.shouldHave(text(text))
        return this
    }
}