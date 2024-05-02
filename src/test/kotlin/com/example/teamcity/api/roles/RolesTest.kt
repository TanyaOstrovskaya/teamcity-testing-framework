package com.example.teamcity.api.roles

import com.example.teamcity.api.BaseApiTest
import com.example.teamcity.api.enums.UserRole.PROJECT_ADMIN
import com.example.teamcity.api.enums.UserRole.SYSTEM_ADMIN
import com.example.teamcity.api.generators.TestDataGenerator
import com.example.teamcity.api.generators.TestDataStorage
import com.example.teamcity.api.requests.checked.CheckedBuildConfig
import com.example.teamcity.api.requests.checked.CheckedProject
import com.example.teamcity.api.requests.unchecked.UncheckedBuildConfig
import com.example.teamcity.api.spec.Specifications
import org.apache.http.HttpStatus
import org.apache.http.HttpStatus.SC_FORBIDDEN
import org.hamcrest.Matchers
import org.testng.annotations.Test

class RolesTest: BaseApiTest() {

    @Test
    fun unauthorizedUserShouldNotHaveRightToCreateProject() {
        val testData = TestDataStorage.addTestData()

        uncheckedWithSuperUser.projectRequest
            .create(testData.project)
            .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)

        uncheckedWithSuperUser.projectRequest
            .get(testData.project.id)
            .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
            .body(Matchers.containsString("No project found by locator 'count:1,id:${testData.project.id}'."))
    }

    @Test
    fun systemAdminShouldHaveRightsToCreateProject() {
        val testData = TestDataStorage.addTestData()

        testData.user.roles = TestDataGenerator.generateRoles(SYSTEM_ADMIN, "g")

        checkedWithSuperUser.userRequest.create(testData.user)
        val project = CheckedProject(Specifications.authSpec(testData.user))
            .create(testData.project)
        softy.assertThat(project.id).isEqualTo(testData.project.id)
    }

    @Test
    fun projectAdminShouldHaveRightsToCreateBuildConfigToTheirProject() {
        val testData = TestDataStorage.addTestData()

        checkedWithSuperUser.projectRequest.create(testData.project)

        testData.user.roles = TestDataGenerator.generateRoles(PROJECT_ADMIN, "p:${testData.project.id}")

        checkedWithSuperUser.userRequest.create(testData.user)

        val buildConfig = CheckedBuildConfig(Specifications.authSpec(testData.user)).create(testData.buildType)
        softy.assertThat(buildConfig.id).isEqualTo(testData.buildType.id)
    }

    @Test
    fun projectAdminShouldNotHaveRightsToCreateBuildConfigToOthersProject() {
        val firstTestData = TestDataStorage.addTestData()
        val secondTestData = TestDataStorage.addTestData()

        checkedWithSuperUser.projectRequest.create(firstTestData.project)
        checkedWithSuperUser.projectRequest.create(secondTestData.project)

        firstTestData.user.roles = TestDataGenerator.generateRoles(PROJECT_ADMIN,  "p:${firstTestData.project.id}")
        checkedWithSuperUser.userRequest.create(firstTestData.user)

        secondTestData.user.roles = TestDataGenerator.generateRoles(PROJECT_ADMIN, "p:${secondTestData.project.id}")
        checkedWithSuperUser.userRequest.create(secondTestData.user)

        UncheckedBuildConfig(Specifications.authSpec(secondTestData.user))
            .create(firstTestData.buildType)
            .then().assertThat().statusCode(SC_FORBIDDEN)
    }
}