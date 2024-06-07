package com.example.teamcity.ui.pages.startup

import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.element
import com.example.teamcity.ui.Selectors.byClassContains
import com.example.teamcity.ui.Selectors.byDataTest
import com.example.teamcity.ui.elements.startup.AgentAuthorizationPopup
import com.example.teamcity.ui.pages.Page
import java.time.Duration


class AgentAuthorizationPage: Page() {

    private val authorizeButton  by lazy { element(byClassContains("AuthorizeAgent__authorizeAgent")) }
    private val authorizationPopup by lazy { AgentAuthorizationPopup(element(byDataTest("ring-popup"))) }

    fun open(): AgentAuthorizationPage {
        Selenide.open("/agents/unauthorized")
        waitUntilPageLoaded()
        return this
    }

    fun authorizeNewAgent() {
        waitUntilPageLoaded()
        authorizeButton.shouldBe(visible, Duration.ofSeconds(20)).click()
        authorizationPopup.authorizeButton.shouldBe(visible, Duration.ofSeconds(20))
        authorizationPopup.submit()
    }
}