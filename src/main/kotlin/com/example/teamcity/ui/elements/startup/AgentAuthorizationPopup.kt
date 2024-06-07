package com.example.teamcity.ui.elements.startup

import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.SelenideElement
import com.example.teamcity.ui.Selectors.byType
import com.example.teamcity.ui.elements.PageElement
import java.time.Duration

class AgentAuthorizationPopup(element: SelenideElement): PageElement(element) {
    val authorizeButton by lazy { findElement(byType("submit")) }

    fun submit() {
        authorizeButton.shouldBe(visible, Duration.ofSeconds(20)).click()
    }
}