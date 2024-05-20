package com.example.teamcity.ui.pages.admin

import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.element
import com.example.teamcity.ui.Selectors.byClass
import com.example.teamcity.ui.Selectors.byId
import com.example.teamcity.ui.pages.Page
import java.time.Duration


class CreateNewProject: Page() {
    private val urlInput = element(byId("url"))
    private val projectNameInput = element(byId("projectName"))
    private val buildTypeNameInput = element(byId("buildTypeName"))


    fun open(parentProjectId: String): CreateNewProject {
        Selenide.open("/admin/createObjectMenu.html?projectId=$parentProjectId&showMode=createProjectMenu")
        waitUntilPageLoaded()
        return this
    }

    fun createProjectByUrl(url: String): CreateNewProject {
        urlInput.sendKeys(url)
        submit()
        return this
    }

    fun setupProject(projectName: String, buildTypeName: String) {
        projectNameInput.shouldBe(visible, Duration.ofSeconds(30)).clear()
        projectNameInput.sendKeys(projectName)
        buildTypeNameInput.clear()
        buildTypeNameInput.sendKeys(buildTypeName)
        waitRingLoaderAbsent()
        submit()
    }
}