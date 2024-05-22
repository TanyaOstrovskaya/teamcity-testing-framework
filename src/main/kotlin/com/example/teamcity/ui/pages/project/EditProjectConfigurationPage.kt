package com.example.teamcity.ui.pages.project

import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.element
import com.example.teamcity.ui.Selectors.byClassContains
import com.example.teamcity.ui.Selectors.byId
import com.example.teamcity.ui.pages.Page
import com.example.teamcity.ui.pages.buildtype.CreateBuildTypePage
import java.time.Duration


class EditProjectConfigurationPage: Page() {

    private val projectCreatedMessage by lazy { element(byId("message_projectCreated")) }
    private val createBuildConfigButton by lazy { element(byClassContains("addNew")) }

    fun checkSubprojectCreateSuccessMessageDisplayed(projectId: String, parentProjectId: String): EditProjectConfigurationPage {
        projectCreatedMessage
            .shouldBe(visible, Duration.ofSeconds(10))
            .has(text("Project \"$parentProjectId / $projectId\" has been successfully created. " +
                      "You can now create a build configuration."))
        return this
    }

    fun clickCreateBuildConfigButton(): CreateBuildTypePage {
        createBuildConfigButton
            .shouldBe(visible, Duration.ofSeconds(10))
            .click()
        return CreateBuildTypePage()
    }
}
