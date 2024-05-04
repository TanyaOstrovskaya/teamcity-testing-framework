package com.example.teamcity.api.project.create.userroles

import com.example.teamcity.api.BaseApiTest
import com.example.teamcity.api.enums.UserRole
import com.example.teamcity.api.enums.UserRole.AGENT_MANAGER
import com.example.teamcity.api.enums.UserRole.PROJECT_ADMIN
import com.example.teamcity.api.enums.UserRole.PROJECT_DEVELOPER
import com.example.teamcity.api.enums.UserRole.PROJECT_VIEWER
import com.example.teamcity.api.enums.UserRole.SYSTEM_ADMIN
import com.example.teamcity.api.generators.TestDataGenerator
import com.example.teamcity.api.generators.TestDataStorage
import com.example.teamcity.api.requests.checked.CheckedProject
import com.example.teamcity.api.requests.unchecked.UncheckedProject
import com.example.teamcity.api.spec.Specifications
import org.apache.http.HttpStatus.SC_FORBIDDEN
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class SubprojectCreateUserRolesTest: BaseApiTest() {

    @Test
    fun systemAdminShouldHaveRightsToCreateSubProject() {
        testData.user.roles = TestDataGenerator.generateRoles(SYSTEM_ADMIN, "g")
        checkedWithSuperUser.userRequest.create(testData.user)

        val project = CheckedProject(Specifications.authSpec(testData.user))
            .create(testData.project)
        val secondTestData = TestDataStorage.addTestData()

        val subProjectDescription = secondTestData.project.copy(parentProject = project)
        val subProject = CheckedProject(Specifications.authSpec(testData.user))
            .create(subProjectDescription)

        softy.assertThat(subProject.id).isEqualTo(subProjectDescription.id)
        softy.assertThat(subProject.parentProjectId).isEqualTo(testData.project.id)
    }

    @Test
    fun projectAdminShouldHaveRightsToCreateSubProject() {
        val project = checkedWithSuperUser.projectRequest.create(testData.project)
        testData.user.roles = TestDataGenerator.generateRoles(PROJECT_ADMIN, "p:${testData.project.id}")
        checkedWithSuperUser.userRequest.create(testData.user)

        val secondTestData = TestDataStorage.addTestData()
        val subProject = CheckedProject(Specifications.authSpec(testData.user))
            .create(secondTestData.project.copy(parentProject = project))

        softy.assertThat(subProject.id).isEqualTo(secondTestData.project.id)
        softy.assertThat(subProject.parentProjectId).isEqualTo(testData.project.id)
    }

    @Test(dataProvider = "roles")
    fun userWithRoleShouldNotHaveRightsToCreateSubProject(role: UserRole) {
        val project = checkedWithSuperUser.projectRequest.create(testData.project)
        testData.user.roles = TestDataGenerator.generateRoles(role, "p:${testData.project.id}")
        checkedWithSuperUser.userRequest.create(testData.user)

        UncheckedProject(Specifications.authSpec(testData.user))
            .create(TestDataStorage.addTestData().project.copy(parentProject = project))
            .then().assertThat().statusCode(SC_FORBIDDEN)
    }

    companion object {
        @DataProvider(name = "roles")
        @JvmStatic
        fun roles(): Array<UserRole> {
            return arrayOf(
                PROJECT_DEVELOPER,
                PROJECT_VIEWER,
                AGENT_MANAGER
            )
        }
    }
}