package com.example.teamcity.ui

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Condition.exist
import com.example.teamcity.api.enums.UserRole.SYSTEM_ADMIN
import com.example.teamcity.api.generators.TestDataGenerator
import com.example.teamcity.api.util.Constants.Companion.ROOT_PROJECT_ID
import com.example.teamcity.ui.pages.ProjectsPage
import com.example.teamcity.ui.pages.admin.CreateNewProject
import org.testng.annotations.Test

class CreateNewProjectTest: BaseUiTest() {

    @Test
    fun authorizedUserShouldBeAbleCreateNewProject() {
        val url = "https://github.com/AlexPshe/spring-core-for-qa"

        testData.user.roles = TestDataGenerator.generateRoles(SYSTEM_ADMIN, "g")
        loginAsUser(testData.user)

        CreateNewProject()
            .open(ROOT_PROJECT_ID)
            .createProjectByUrl(url)
            .setupProject(testData.project.name!!, testData.buildType.name!!)

        ProjectsPage()
            .open()
            .getSubprojects()
            .last()
            .header
            .should(exist)
            .shouldHave(Condition.text(testData.project.name))

    }
}