package com.example.teamcity.ui.pages.startup

import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.element
import com.example.teamcity.ui.Selectors.byDataTest
import com.example.teamcity.ui.pages.Page
import java.time.Duration

class AgentsPage: Page()  {

    private val agent  by lazy { element(byDataTest("agent")) }

    fun open(): AgentsPage {
        Selenide.open("/agents")
        return this
    }

    fun checkAgentIsShown() {
        waitUntilPageLoaded()
        agent.shouldBe(visible, Duration.ofSeconds(20))
    }
}