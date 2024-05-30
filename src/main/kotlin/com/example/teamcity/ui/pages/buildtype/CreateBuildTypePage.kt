package com.example.teamcity.ui.pages.buildtype

import com.codeborne.selenide.Condition.enabled
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.element
import com.example.teamcity.ui.Selectors.byText
import com.example.teamcity.ui.pages.Page
import java.time.Duration.ofSeconds

class CreateBuildTypePage: Page() {

    private val fromRepositoryUrlButton by lazy { element(byText("From a repository URL")) }
    private val manuallyButton by lazy { element(byText("Manually")) }

    fun open(projectId: String): CreateBuildTypePage {
        Selenide.open("/admin/createObjectMenu.html?projectId=$projectId&showMode=createBuildTypeMenu")
        waitUntilPageLoaded()
        return this
    }

    fun switchFromRepositoryUrlMode(): CreateBuildTypeFromRepositoryUrlPage {
        fromRepositoryUrlButton.shouldBe(visible, ofSeconds(10))
        fromRepositoryUrlButton.shouldBe(enabled, ofSeconds(10))
        fromRepositoryUrlButton.click()
        waitRingLoaderAbsent()
        return CreateBuildTypeFromRepositoryUrlPage()
    }

    fun switchManuallyMode(): CreateBuildTypeManuallyPage {
        manuallyButton.shouldBe(visible, ofSeconds(10)).click()
        waitRingLoaderAbsent()
        return CreateBuildTypeManuallyPage()
    }
}
