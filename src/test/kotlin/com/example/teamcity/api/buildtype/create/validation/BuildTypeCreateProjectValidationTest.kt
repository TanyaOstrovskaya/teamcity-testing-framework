package com.example.teamcity.api.buildtype.create.validation

import com.example.teamcity.api.BaseApiTest
import com.example.teamcity.api.enums.UserRole.PROJECT_ADMIN
import com.example.teamcity.api.generators.RandomData
import com.example.teamcity.api.generators.TestDataGenerator
import com.example.teamcity.api.generators.TestDataStorage
import com.example.teamcity.api.models.Project
import com.example.teamcity.api.requests.unchecked.UncheckedBuildConfig
import com.example.teamcity.api.spec.Specifications
import com.example.teamcity.api.util.Constants.Companion.ROOT_PROJECT_ID
import org.apache.http.HttpStatus.SC_BAD_REQUEST
import org.apache.http.HttpStatus.SC_FORBIDDEN
import org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR
import org.apache.http.HttpStatus.SC_NOT_FOUND
import org.hamcrest.Matchers.containsString
import org.testng.annotations.Test

class BuildTypeCreateProjectValidationTest: BaseApiTest() {

    @Test
    fun buildWithAbsentProjectCannotBeCreated() {
        uncheckedWithSuperUser.buildConfigRequest
            .create(testData.buildType.copy(project = null))
            .then()
            .assertThat()
            .statusCode(SC_BAD_REQUEST)
            .body(containsString( "Build type creation request should contain project node."))
    }

    @Test
    fun buildCannotBeCreatedToRootProject() {
        uncheckedWithSuperUser.buildConfigRequest
            .create(testData.buildType.copy(project = Project(
                locator = ROOT_PROJECT_ID
            )))
            .then()
            .assertThat()
            .statusCode(SC_INTERNAL_SERVER_ERROR)   // todo: report bug, answer is 500, should be 400
            .body(containsString( "Root project cannot contain build configurations."))
    }

    @Test
    fun buildCannotBeCreatedToNonExistingProject() {
        val nonExistingProjectId = RandomData.getString()
        uncheckedWithSuperUser.buildConfigRequest
            .create(testData.buildType.copy(project = Project(locator = nonExistingProjectId)))
            .then()
            .assertThat()
            .statusCode(SC_NOT_FOUND)
            .body(containsString("No project found by name or internal/external id '$nonExistingProjectId'"))
    }

    @Test
    fun buildCannotBeCreatedToOtherUsersProjectIfNoPermissions() {
        val secondTestData = TestDataStorage.addTestData()

        val firstProject = checkedWithSuperUser.projectRequest.create(testData.project)
        val secondProject = checkedWithSuperUser.projectRequest.create(secondTestData.project)
        testData.user.roles = TestDataGenerator.generateRoles(PROJECT_ADMIN, "p:${testData.project.id}")
        checkedWithSuperUser.userRequest.create(testData.user)

        UncheckedBuildConfig(Specifications.authSpec(testData.user))
            .create(secondTestData.buildType.copy(project = secondProject))
            .then()
            .assertThat()
            .statusCode(SC_FORBIDDEN)
            .body(containsString(" You do not have enough permissions to edit project with id: ${secondProject.id}"))
    }

}