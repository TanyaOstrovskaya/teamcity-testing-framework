package com.example.teamcity.api.buildType

import com.example.teamcity.api.BaseApiTest
import com.example.teamcity.api.enums.UserRole.PROJECT_ADMIN
import com.example.teamcity.api.generators.TestData
import com.example.teamcity.api.generators.TestDataGenerator
import com.example.teamcity.api.generators.TestDataStorage
import com.example.teamcity.api.requests.checked.CheckedBuildConfig
import com.example.teamcity.api.requests.unchecked.UncheckedBuildConfig
import com.example.teamcity.api.spec.Specifications
import org.apache.http.HttpStatus.SC_FORBIDDEN
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class BuildTypeCreateUserRolesTest: BaseApiTest() {

    lateinit var testData: TestData

    @BeforeMethod
    fun beforeTest() {
        testData = TestDataStorage.addTestData()
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
}