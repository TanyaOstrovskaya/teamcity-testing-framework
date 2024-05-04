package com.example.teamcity.api.project.create.userroles

import com.example.teamcity.api.BaseApiTest
import com.example.teamcity.api.enums.UserRole.SYSTEM_ADMIN
import com.example.teamcity.api.generators.TestDataGenerator
import com.example.teamcity.api.models.NewProjectDescription
import com.example.teamcity.api.requests.checked.CheckedProject
import com.example.teamcity.api.requests.unchecked.UncheckedProject
import com.example.teamcity.api.spec.Specifications
import org.apache.http.HttpStatus.SC_FORBIDDEN
import org.apache.http.HttpStatus.SC_NOT_FOUND
import org.apache.http.HttpStatus.SC_UNAUTHORIZED
import org.hamcrest.Matchers
import org.testng.annotations.Test

class ProjectCreateUserRolesTest: BaseApiTest() {

    @Test
    fun unauthorizedUserShouldNotHaveRightToCreateProject() {
        checkedWithSuperUser.userRequest.create(testData.user)
        UncheckedProject(Specifications.unauthSpec())
            .create(testData.project)
            .then().assertThat().statusCode(SC_UNAUTHORIZED)

        checkProjectNotCreated(testData.project)
    }

    @Test
    fun authorizedRegularUserShouldNotHaveRightToCreateProject() {
        checkedWithSuperUser.userRequest.create(testData.user)
        UncheckedProject(Specifications.authSpec(testData.user))
            .create(testData.project)
            .then().assertThat().statusCode(SC_FORBIDDEN)

        checkProjectNotCreated(testData.project)
    }

    @Test
    fun systemAdminShouldHaveRightsToCreateProject() {
        testData.user.roles = TestDataGenerator.generateRoles(SYSTEM_ADMIN, "g")
        checkedWithSuperUser.userRequest.create(testData.user)
        val project = CheckedProject(Specifications.authSpec(testData.user))
            .create(testData.project)

        softy.assertThat(project.id).isEqualTo(testData.project.id)
    }

    @Test
    fun superUserShouldHaveRightsToCreateProject() {
        val project = checkedWithSuperUser.projectRequest.create(testData.project)

        softy.assertThat(project.id).isEqualTo(testData.project.id)
    }

    private fun checkProjectNotCreated(project: NewProjectDescription) {
        uncheckedWithSuperUser.projectRequest
            .get(project.id!!)
            .then().assertThat().statusCode(SC_NOT_FOUND)
            .body(Matchers.containsString("No project found by locator 'count:1,id:${project.id}'."))
    }
}