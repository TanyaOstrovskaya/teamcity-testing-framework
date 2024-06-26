package com.example.teamcity.ui.pages.project

import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.element
import com.example.teamcity.ui.Selectors.byId
import com.example.teamcity.ui.Selectors.byText
import com.example.teamcity.ui.elements.project.create.CreateProjectByUrlElement
import com.example.teamcity.ui.elements.project.create.CreateProjectManuallyElement
import com.example.teamcity.ui.pages.Page
import java.time.Duration

class CreateProjectPage: Page() {

    private val createProjectByUrl by lazy { CreateProjectByUrlElement(element(byId("container"))) }
    private val createProjectManually by lazy { CreateProjectManuallyElement(element(byId("container"))) }
    private val manuallyButton by lazy { element(byText("Manually")) }

    fun open(projectId: String): CreateProjectPage {
        Selenide.open("/admin/createObjectMenu.html?projectId=$projectId&showMode=createProjectMenu")
        waitUntilPageLoaded()
        return this
    }

    fun createProjectByUrl(url: String): CreateProjectPage {
        createProjectByUrl.urlInput.shouldBe(visible, Duration.ofSeconds(30)).clear()
        createProjectByUrl.urlInput.sendKeys(url)
        submit()
        return this
    }

    fun createProjectManually(projectName: String,
                              projectId: String): EditProjectConfigurationPage {
        manuallyButton.click()
        createProjectManually.projectName.shouldBe(visible, Duration.ofSeconds(10)).clear()
        createProjectManually.projectName.sendKeys(projectName)
        createProjectManually.projectId.clear()
        createProjectManually.projectId.sendKeys(projectId)
        submit()
        waitRingLoaderAbsent()
        return EditProjectConfigurationPage()
    }

    fun setupProjectByUrl(projectName: String,
                          buildTypeName: String): CreateProjectPage {
        createProjectByUrl.projectNameInput.shouldBe(visible, Duration.ofSeconds(30)).clear()
        createProjectByUrl.projectNameInput.sendKeys(projectName)
        createProjectByUrl.buildTypeNameInput.clear()
        createProjectByUrl.buildTypeNameInput.sendKeys(buildTypeName)
        waitRingLoaderAbsent()
        submit()
        return this
    }

    fun checkErrorOnCreation() {
        createProjectByUrl.errorOnCreation
            .shouldBe(visible, Duration.ofSeconds(10))
            .has(text("No such device or address"))
    }
}
