package com.example.teamcity.ui.pages.buildtype

import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.element
import com.example.teamcity.ui.Selectors.byId
import com.example.teamcity.ui.pages.Page
import java.time.Duration

class BuildTypePage : Page()  {

    private val buildTitle by lazy { element(byId("restPageTitle")) }
    private val buildSteps by lazy { element(byId("discoveredRunners")) }
    private val vcsRoots by lazy { element(byId("vcsRoots")) }

    fun checkBuildTypeTitle(title: String): BuildTypePage {
        buildTitle
            .shouldBe(visible, Duration.ofSeconds(10))
            .has(text(title))
        return this
    }

    fun checkBuildStepsDisplayed(): BuildTypePage {
        buildSteps.shouldBe(visible, Duration.ofSeconds(10))
        return this
    }

    fun checkVcsRootsDisplayed(): BuildTypePage {
        vcsRoots.shouldBe(visible, Duration.ofSeconds(10))
        return this
    }
}
