package com.example.teamcity.ui.pages.buildtype

import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.element
import com.example.teamcity.ui.Selectors.byClass
import com.example.teamcity.ui.Selectors.byId
import com.example.teamcity.ui.pages.Page
import java.time.Duration.ofSeconds

class CreateBuildTypeFromRepositoryUrlPage: Page() {

    private val urlInput by lazy { element(byId("url")) }
    private val parentIdSelect by lazy { element(byId("parentIdSelect")) }
    private val buildTypeName by lazy { element(byId("buildTypeName")) }
    private val connectionMsg by lazy { element(byClass("connectionSuccessful")) }

    fun createBuildTypeByUrl(url: String): CreateBuildTypeFromRepositoryUrlPage {
        urlInput.shouldBe(visible, ofSeconds(30)).clear()
        urlInput.sendKeys(url)
        submit()
        waitRingLoaderAbsent()
        return this
    }

    fun inputBuildTypeName(name: String): CreateBuildTypeFromRepositoryUrlPage {
        buildTypeName
            .shouldBe(visible, ofSeconds(10))
            .sendKeys(name)
        return this
    }

    fun checkParentProjectSelected(parentProjectFullName: String): CreateBuildTypeFromRepositoryUrlPage {
        parentIdSelect
            .shouldBe(visible, ofSeconds(10))
            .has(text(parentProjectFullName))
        return this
    }

    fun checkConnectionSuccessfulMessage(): CreateBuildTypeFromRepositoryUrlPage {
        connectionMsg
            .shouldBe(visible, ofSeconds(10))
            .has(text("The connection to the VCS repository has been verified"))
        return this
    }
}
