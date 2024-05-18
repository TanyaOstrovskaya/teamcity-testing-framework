package com.example.teamcity.api.buildtype.create.validation

import com.example.teamcity.api.BaseApiTest
import com.example.teamcity.api.enums.UserRole.PROJECT_ADMIN
import com.example.teamcity.api.enums.UserRole.SYSTEM_ADMIN
import com.example.teamcity.api.generators.RandomData
import com.example.teamcity.api.generators.TestDataGenerator
import com.example.teamcity.api.generators.TestDataStorage
import com.example.teamcity.api.requests.checked.CheckedBuildConfig
import com.example.teamcity.api.requests.checked.CheckedProject
import com.example.teamcity.api.requests.unchecked.UncheckedBuildConfig
import com.example.teamcity.api.spec.Specifications
import org.apache.http.HttpStatus.SC_BAD_REQUEST
import org.hamcrest.Matchers.containsString
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

private const val MAX_NAME_LENGTH = 1000

class BuildTypeCreateNameValidationTest: BaseApiTest() {

    @Test(dataProvider = "validNames")
    fun buildTypeWithValidNameCanBeCreated(name: String) {
        val project = checkedWithSuperUser.projectRequest.create(testData.project)

        val buildConfig = checkedWithSuperUser.buildConfigRequest.create(
            testData.buildType.copy(
                name = name,
                project = project
            )
        )

        softy.assertThat(buildConfig.name).isEqualTo(name)
    }

    @Test(dataProvider = "invalidNames")
    fun buildTypeWithInvalidNameCannotBeCreated(name: String?) {
        val project = checkedWithSuperUser.projectRequest.create(testData.project)

        uncheckedWithSuperUser.buildConfigRequest
            .create(testData.buildType.copy(
                name = name,
                project = project
            ))
            .then()
            .assertThat()
            .statusCode(SC_BAD_REQUEST)
            .body(containsString("When creating a build type, non empty name should be provided."))
    }

    @Test
    fun buildTypeWithSameNameCannotBeCreatedInOneProject() {
        val project = checkedWithSuperUser.projectRequest.create(testData.project)
        testData.user.roles = TestDataGenerator.generateRoles(PROJECT_ADMIN, "p:${testData.project.id}")
        checkedWithSuperUser.userRequest.create(testData.user)

        val firstBuildType = CheckedBuildConfig(Specifications.authSpec(testData.user))
            .create(testData.buildType)

        val secondTestData = TestDataStorage.addTestData()
        UncheckedBuildConfig(Specifications.authSpec(testData.user))
            .create(
                secondTestData.buildType.copy(
                    project = project,
                    name = testData.buildType.name
                )
            )
            .then()
            .assertThat()
            .statusCode(SC_BAD_REQUEST)
            .body(containsString(" Build configuration with name \"${testData.buildType.name}\" already exists in project: \"${project.name}\""))
    }

    @Test
    fun buildTypeWithSameNameCanBeCreatedInDifferentProjects() {
        val firstProject = checkedWithSuperUser.projectRequest.create(testData.project)
        testData.user.roles = TestDataGenerator.generateRoles(SYSTEM_ADMIN, "g")
        checkedWithSuperUser.userRequest.create(testData.user)

        val secondTestData = TestDataStorage.addTestData()
        val secondProject = CheckedProject(Specifications.authSpec(testData.user)).create(secondTestData.project)

        checkedWithSuperUser.buildConfigRequest.create(testData.buildType)
        val secondBuildType = CheckedBuildConfig(Specifications.authSpec(testData.user))
            .create(secondTestData.buildType.copy(name = testData.buildType.name))

        softy.assertThat(secondBuildType.name).isEqualTo(testData.buildType.name)
    }

    companion object {
        @DataProvider(name = "validNames")
        @JvmStatic
        fun validNames(): Array<String> {
            return arrayOf(
                RandomData.getString(),
                RandomData.getString(MAX_NAME_LENGTH),
                RandomData.getNumeric(),
                "\t${RandomData.getString()}   ${RandomData.getString()}",
                "${RandomData.getString()} \\ ${RandomData.getString()}",
            )
        }

        @DataProvider(name = "invalidNames")
        @JvmStatic
        fun invalidNames(): Array<String?> {
            return arrayOf(
                null,
                "",
                // todo: report a bug about name length. worked with 10.000 symbols, and it looks awful at UI
                // RandomData.getString(MAX_NAME_LENGTH + 1)
            )
        }
    }
}
