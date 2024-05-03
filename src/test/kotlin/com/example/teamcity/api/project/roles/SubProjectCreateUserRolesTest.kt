package com.example.teamcity.api.project.roles

import com.example.teamcity.api.BaseApiTest
import com.example.teamcity.api.enums.UserRole
import com.example.teamcity.api.enums.UserRole.AGENT_MANAGER
import com.example.teamcity.api.enums.UserRole.PROJECT_ADMIN
import com.example.teamcity.api.enums.UserRole.PROJECT_DEVELOPER
import com.example.teamcity.api.enums.UserRole.PROJECT_VIEWER
import com.example.teamcity.api.enums.UserRole.SYSTEM_ADMIN
import com.example.teamcity.api.generators.TestData
import com.example.teamcity.api.generators.TestDataGenerator
import com.example.teamcity.api.generators.TestDataStorage
import com.example.teamcity.api.requests.checked.CheckedProject
import com.example.teamcity.api.requests.unchecked.UncheckedProject
import com.example.teamcity.api.spec.Specifications
import org.apache.http.HttpStatus.SC_FORBIDDEN
import org.testng.annotations.BeforeMethod
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class SubProjectCreateUserRolesTest: BaseApiTest() {

    lateinit var testData: TestData

    @BeforeMethod
    fun beforeTest() {
        testData = TestDataStorage.addTestData()
    }

    @Test
    fun systemAdminShouldHaveRightsToCreateSubProject() {
        testData.user.roles = TestDataGenerator.generateRoles(SYSTEM_ADMIN, "g")
        checkedWithSuperUser.userRequest.create(testData.user)

        val project = CheckedProject(Specifications.authSpec(testData.user))
            .create(testData.project)
        val subProjectDescription = TestDataGenerator.generateProject(project)
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

        val subProjectDescription = TestDataGenerator.generateProject(project)
        val subProject = CheckedProject(Specifications.authSpec(testData.user))
            .create(subProjectDescription)

        softy.assertThat(subProject.id).isEqualTo(subProjectDescription.id)
        softy.assertThat(subProject.parentProjectId).isEqualTo(testData.project.id)
    }

    @Test(dataProvider = "roles")
    fun userWithRoleShouldNotHaveRightsToCreateSubProject(role: UserRole) {
        val project = checkedWithSuperUser.projectRequest.create(testData.project)
        testData.user.roles = TestDataGenerator.generateRoles(role, "p:${testData.project.id}")
        checkedWithSuperUser.userRequest.create(testData.user)

        val subProjectDescription = TestDataGenerator.generateProject(project)

        UncheckedProject(Specifications.authSpec(testData.user))
            .create(subProjectDescription)
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