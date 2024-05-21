package com.example.teamcity.ui.pages.project

import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.element
import com.example.teamcity.ui.Selectors.byId
import com.example.teamcity.ui.pages.Page
import java.time.Duration

class EditProjectConfigurationPage: Page() {

    private val projectCreatedMessage by lazy { element(byId("message_projectCreated")) }

    fun checkSubprojectCreateSuccessMessageDisplayed(projectId: String, parentProjectId: String) {
        projectCreatedMessage
            .shouldBe(visible, Duration.ofSeconds(10))
            .has(text("Project \"$parentProjectId / $projectId\" has been successfully created. " +
                      "You can now create a build configuration."))
    }
}
