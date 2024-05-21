package com.example.teamcity.ui.project.create

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Condition.exist
import com.example.teamcity.api.enums.UserRole.PROJECT_ADMIN
import com.example.teamcity.api.enums.UserRole.SYSTEM_ADMIN
import com.example.teamcity.api.generators.TestDataGenerator
import com.example.teamcity.api.generators.TestDataStorage
import com.example.teamcity.api.util.Constants.Companion.ROOT_PROJECT_ID
import com.example.teamcity.ui.BaseUiTest
import com.example.teamcity.ui.pages.project.ProjectPage
import com.example.teamcity.ui.pages.project.ProjectsPage
import com.example.teamcity.ui.pages.project.create.CreateProjectPage
import org.testng.annotations.Test

class CreateProjectTest: BaseUiTest() {

    @Test
    fun systemAdminShouldBeAbleToCreateNewProjectFromRepositoryUrl() {
        val url = "https://github.com/AlexPshe/spring-core-for-qa"

        testData.user.roles = TestDataGenerator.generateRoles(SYSTEM_ADMIN, "g")
        loginAsUser(testData.user)

        CreateProjectPage()
            .open(ROOT_PROJECT_ID)
            .createProjectByUrl(url)
            .setupProjectByUrl(testData.project.name!!, testData.buildType.name!!)

        ProjectsPage()
            .open()
            .getSubprojects()
            .last()
            .header
            .should(exist)
            .shouldHave(Condition.text(testData.project.name!!))

    }

    @Test
    fun projectAdminShouldBeAbleToCreateSubProjectWithManuallyMode() {
        val secondTestData = TestDataStorage.addTestData()
        val project = checkedWithSuperUser.projectRequest.create(testData.project)
        testData.user.roles = TestDataGenerator.generateRoles(PROJECT_ADMIN, "p:${testData.project.id}")

        loginAsUser(testData.user)

        ProjectPage(project)
            .open()
            .openCreateSubprojectPage()
            .createProjectManually(secondTestData.project.name!!, secondTestData.project.id!!)
            .checkSubprojectCreateSuccessMessageDisplayed(secondTestData.project.id!!, testData.project.id!!)
        ProjectPage(project)
            .open()
            .getSubprojects()
            .last()
            .header
            .should(exist)
            .shouldHave(Condition.text(secondTestData.project.name!!))
    }

    @Test
    fun projectCannotBeCreatedByUrlToNotExistingRepository() {
        val url = "https://github.com/AlexPshe/spring-core-for-qaaaaaaaaaaaaaaaaaa"

        testData.user.roles = TestDataGenerator.generateRoles(SYSTEM_ADMIN, "g")
        loginAsUser(testData.user)

        CreateProjectPage()
            .open(ROOT_PROJECT_ID)
            .createProjectByUrl(url)
            .checkErrorOnCreation()
    }
}