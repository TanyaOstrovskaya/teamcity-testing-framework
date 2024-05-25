package com.example.teamcity.ui.buildtype.create

import com.example.teamcity.api.enums.UserRole.PROJECT_ADMIN
import com.example.teamcity.api.enums.UserRole.SYSTEM_ADMIN
import com.example.teamcity.api.generators.TestDataGenerator
import com.example.teamcity.api.generators.TestDataStorage
import com.example.teamcity.api.util.Constants.Companion.ROOT_PROJECT_ID
import com.example.teamcity.ui.BaseUiTest
import com.example.teamcity.ui.pages.buildtype.BuildTypePage
import com.example.teamcity.ui.pages.project.CreateProjectPage
import com.example.teamcity.ui.pages.project.ProjectPage
import com.example.teamcity.ui.pages.project.ProjectsPage
import org.testng.annotations.Test

class CreateBuildTypeTest: BaseUiTest() {

    @Test
    fun buildTypeCanBeCreatedFromUrlDuringProjectCreationFlow() {
        val url = "https://github.com/AlexPshe/spring-core-for-qa"

        testData.user.roles = TestDataGenerator.generateRoles(SYSTEM_ADMIN, "g")
        loginAsUser(testData.user)

        CreateProjectPage()
            .open(ROOT_PROJECT_ID)
            .createProjectManually(testData.project.name!!, testData.project.id!!)
            .clickCreateBuildConfigButton()
            .switchFromRepositoryUrlMode()
            .createBuildTypeByUrl(url)
            .checkConnectionSuccessfulMessage()
            .inputBuildTypeName(testData.buildType.name!!)
            .submit()

        BuildTypePage()
            .checkBuildTypeTitle(testData.buildType.name!!)
            .checkBuildStepsDisplayed()
    }

    @Test
    fun buildTypeCanBeCreatedManuallyInExistingProject() {
        val url = "https://github.com/AlexPshe/spring-core-for-qa"
        val secondTestData = TestDataStorage.addTestData()

        val project = checkedWithSuperUser.projectRequest.create(testData.project)
        testData.user.roles = TestDataGenerator.generateRoles(PROJECT_ADMIN, "p:${testData.project.id}")

        loginAsUser(testData.user)

        ProjectPage(project)
            .open()
            .openCreateBuildTypePage()
            .switchManuallyMode()
            .createBuildTypeManually(secondTestData.buildType.name!!, secondTestData.buildType.id!!)
            .inputRepositoryUrl(url)

        BuildTypePage()
            .checkBuildTypeTitle(secondTestData.buildType.name!!)
            .checkVcsRootsDisplayed()
    }

    @Test
    fun buildTypeCannotBeCreatedWithEmptyName() {
        val project = checkedWithSuperUser.projectRequest.create(testData.project)
        testData.user.roles = TestDataGenerator.generateRoles(SYSTEM_ADMIN, "g")
        loginAsUser(testData.user)

        ProjectsPage()
            .waitProjectsLoaded()
            .waitUntilFavouritePageLoaded()
        ProjectPage(project)
            .open()
            .openCreateBuildTypePage()
            .switchManuallyMode()
            .createBuildTypeManually("", "")
            .checkErrorBuildTypeName("Name must not be empty")
            .checkErrorBuildTypeId("The ID field must not be empty.")
    }
}
