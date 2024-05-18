package com.example.teamcity.api.project.create.validation

import com.example.teamcity.api.BaseApiTest
import com.example.teamcity.api.enums.UserRole.PROJECT_DEVELOPER
import com.example.teamcity.api.enums.UserRole.SYSTEM_ADMIN
import com.example.teamcity.api.generators.TestDataGenerator
import com.example.teamcity.api.generators.TestDataStorage
import com.example.teamcity.api.models.Project
import com.example.teamcity.api.requests.checked.CheckedProject
import com.example.teamcity.api.requests.unchecked.UncheckedProject
import com.example.teamcity.api.spec.Specifications
import com.example.teamcity.api.util.Constants.Companion.ROOT_PROJECT_ID
import org.apache.http.HttpStatus.SC_FORBIDDEN
import org.apache.http.HttpStatus.SC_NOT_FOUND
import org.hamcrest.Matchers.containsString
import org.testng.annotations.Test

class ProjectCreateParentValidationTest: BaseApiTest() {

    @Test
    fun projectWithAbsentParentIsCreatedToRoot() {
        val project = checkedWithSuperUser.projectRequest
            .create(testData.project.copy(parentProject = null))

        softy.assertThat(project.id).isEqualTo(testData.project.id)
        softy.assertThat(project.parentProjectId).isEqualTo(ROOT_PROJECT_ID)
    }

    @Test
    fun projectWithRootParentIsCreatedToRoot() {
        testData.user.roles = TestDataGenerator.generateRoles(SYSTEM_ADMIN, "g")
        checkedWithSuperUser.userRequest.create(testData.user)
        val project = CheckedProject(Specifications.authSpec(testData.user))
            .create(testData.project.copy(parentProject = Project(locator = ROOT_PROJECT_ID)))

        softy.assertThat(project.id).isEqualTo(testData.project.id)
        softy.assertThat(project.parentProjectId).isEqualTo(ROOT_PROJECT_ID)
    }

    @Test
    fun projectWithNonExistingParentCannotBeCreated() {
        val nonExistingProjectId = "blah"
        uncheckedWithSuperUser.projectRequest.create(
            testData.project.copy(
                parentProject = Project(
                    locator = nonExistingProjectId,
                )
            )
        )
            .then()
            .assertThat()
            .statusCode(SC_NOT_FOUND)
            .body(containsString("No project found by name or internal/external id '$nonExistingProjectId'"))
    }

    @Test
    fun projectCannotBeCreatedInsideOtherUsersProjectIfNoPermissions() {
        val superUserProject = checkedWithSuperUser.projectRequest.create(testData.project)
        testData.user.roles = TestDataGenerator.generateRoles(PROJECT_DEVELOPER, "g")
        checkedWithSuperUser.userRequest.create(testData.user)

        val secondTestData = TestDataStorage.addTestData()
        UncheckedProject(Specifications.authSpec(testData.user))
            .create(secondTestData.project.copy(parentProject = superUserProject))
            .then()
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body(containsString("You do not have \"Create subproject\" permission in project"))
    }
}
