package com.example.teamcity.api.buildtype.create.userroles

import com.example.teamcity.api.BaseApiTest
import com.example.teamcity.api.enums.UserRole
import com.example.teamcity.api.enums.UserRole.AGENT_MANAGER
import com.example.teamcity.api.enums.UserRole.PROJECT_ADMIN
import com.example.teamcity.api.enums.UserRole.PROJECT_DEVELOPER
import com.example.teamcity.api.enums.UserRole.PROJECT_VIEWER
import com.example.teamcity.api.generators.TestDataGenerator
import com.example.teamcity.api.generators.TestDataStorage
import com.example.teamcity.api.requests.checked.CheckedBuildConfig
import com.example.teamcity.api.requests.unchecked.UncheckedBuildConfig
import com.example.teamcity.api.spec.Specifications
import org.apache.http.HttpStatus.SC_FORBIDDEN
import org.apache.http.HttpStatus.SC_UNAUTHORIZED
import org.hamcrest.Matchers.containsString
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class BuildTypeCreateUserRolesTest: BaseApiTest() {

    @Test
    fun unauthorizedUserShouldNotHaveRightToCreateBuildType() {
        checkedWithSuperUser.userRequest.create(testData.user)
        UncheckedBuildConfig(Specifications.unauthSpec())
            .create(testData.buildType)
            .then().assertThat().statusCode(SC_UNAUTHORIZED)
    }

    @Test
    fun projectAdminShouldHaveRightsToCreateBuildConfigToTheirProject() {
        checkedWithSuperUser.projectRequest.create(testData.project)
        testData.user.roles = TestDataGenerator.generateRoles(PROJECT_ADMIN, "p:${testData.project.id}")
        checkedWithSuperUser.userRequest.create(testData.user)
        val buildConfig = CheckedBuildConfig(Specifications.authSpec(testData.user)).create(testData.buildType)

        softy.assertThat(buildConfig.id).isEqualTo(testData.buildType.id)
    }

    @Test
    fun projectAdminShouldNotHaveRightsToCreateBuildConfigToOthersProject() {
        val secondTestData = TestDataStorage.addTestData()

        checkedWithSuperUser.projectRequest.create(testData.project)
        testData.user.roles = TestDataGenerator.generateRoles(PROJECT_ADMIN,  "p:${testData.project.id}")
        checkedWithSuperUser.userRequest.create(testData.user)

        checkedWithSuperUser.projectRequest.create(secondTestData.project)
        secondTestData.user.roles = TestDataGenerator.generateRoles(PROJECT_ADMIN, "p:${secondTestData.project.id}")
        checkedWithSuperUser.userRequest.create(secondTestData.user)

        UncheckedBuildConfig(Specifications.authSpec(secondTestData.user))
            .create(testData.buildType)
            .then().assertThat().statusCode(SC_FORBIDDEN)
    }

    @Test(dataProvider = "forbiddenRoles")
    fun userWithRoleShouldNotHaveRightsToCreateBuildConfigToTheirProject(role: UserRole) {
        checkedWithSuperUser.projectRequest.create(testData.project)
        testData.user.roles = TestDataGenerator.generateRoles(role, "p:${testData.project.id}")
        checkedWithSuperUser.userRequest.create(testData.user)

        UncheckedBuildConfig(Specifications.authSpec(testData.user))
            .create(testData.buildType)
            .then().assertThat().statusCode(SC_FORBIDDEN)
            .body(containsString("You do not have enough permissions to edit project with id: ${testData.project.id}"))
    }

    companion object {
        @DataProvider(name = "forbiddenRoles")
        @JvmStatic
        fun forbiddenRoles(): Array<UserRole> {
            return arrayOf(
                PROJECT_DEVELOPER,
                PROJECT_VIEWER,
                AGENT_MANAGER
            )
        }
    }
}