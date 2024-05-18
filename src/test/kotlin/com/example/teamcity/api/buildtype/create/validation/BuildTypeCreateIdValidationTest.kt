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
import org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR
import org.hamcrest.Matchers.containsString
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

private const val MAX_ID_LENGTH = 225

class BuildTypeCreateIdValidationTest: BaseApiTest() {

    @Test(dataProvider = "validIds")
    fun buildTypeWithValidIdCanBeCreated(id: String) {
        val project = checkedWithSuperUser.projectRequest.create(testData.project)
        val buildConfig = checkedWithSuperUser.buildConfigRequest.create(
            testData.buildType.copy(
                id = id,
                project = project
            )
        )

        softy.assertThat(buildConfig.id).isEqualTo(id)
    }

    @Test(dataProvider = "invalidIds")
    fun buildTypeWithInvalidIdCannotBeCreated(id: String?, errorString: String) {
        val project = checkedWithSuperUser.projectRequest.create(testData.project)
        uncheckedWithSuperUser.buildConfigRequest
            .create(testData.buildType.copy(
                id = id,
                project = project
            ))
            .then()
            .assertThat()
            .statusCode(SC_INTERNAL_SERVER_ERROR)   // todo: report bug, answer is 500, should be 400
            .body(containsString(errorString))
    }

    @Test
    fun buildTypeWithAbsentIdCreatedWithAutogeneratedId() {
        val project = checkedWithSuperUser.projectRequest.create(testData.project)
        testData.user.roles = TestDataGenerator.generateRoles(PROJECT_ADMIN, "p:${testData.project.id}")
        checkedWithSuperUser.userRequest.create(testData.user)

        val buildConfig = CheckedBuildConfig(Specifications.authSpec(testData.user))
            .create(
                testData.buildType.copy(
                    id = null,
                    project = project
                )
            )

        softy.assertThat(buildConfig.id).isNotEmpty()
    }

    @Test
    fun buildTypeIdCanBeTheSameAsProjectId() {
        val project = checkedWithSuperUser.projectRequest.create(testData.project)
        testData.user.roles = TestDataGenerator.generateRoles(PROJECT_ADMIN, "p:${testData.project.id}")
        checkedWithSuperUser.userRequest.create(testData.user)

        val buildConfig = CheckedBuildConfig(Specifications.authSpec(testData.user))
            .create(
                testData.buildType.copy(
                    id = testData.project.id,
                    project = project
                )
            )

        softy.assertThat(buildConfig.id).isEqualTo(testData.project.id)
    }

    @Test
    fun buildTypeWithSameIdCannotBeCreatedInOneProject() {
        val project = checkedWithSuperUser.projectRequest.create(testData.project)
        testData.user.roles = TestDataGenerator.generateRoles(SYSTEM_ADMIN, "g")
        checkedWithSuperUser.userRequest.create(testData.user)
        checkedWithSuperUser.buildConfigRequest.create(testData.buildType)

        val secondTestData = TestDataStorage.addTestData()
        UncheckedBuildConfig(Specifications.authSpec(testData.user))
            .create(
                secondTestData.buildType.copy(
                    project = project,
                    id = testData.buildType.id
                )
            )
            .then()
            .assertThat()
            .statusCode(SC_BAD_REQUEST)
            .body(containsString("The build configuration / template ID \"${testData.buildType.id}\" is already used by another configuration or template"))
    }

    @Test
    fun buildTypeWithSameIdCannotBeCreatedInDifferentProjects() {
        val firstProject = checkedWithSuperUser.projectRequest.create(testData.project)
        testData.user.roles = TestDataGenerator.generateRoles(SYSTEM_ADMIN, "g")
        checkedWithSuperUser.userRequest.create(testData.user)

        val secondTestData = TestDataStorage.addTestData()
        val secondProject = CheckedProject(Specifications.authSpec(testData.user)).create(secondTestData.project)

        checkedWithSuperUser.buildConfigRequest.create(testData.buildType)
        UncheckedBuildConfig(Specifications.authSpec(testData.user))
            .create(secondTestData.buildType.copy(id = testData.buildType.id,))
            .then()
            .assertThat()
            .statusCode(SC_BAD_REQUEST)
            .body(containsString("The build configuration / template ID \"${testData.buildType.id}\" is already used by another configuration or template"))
    }


    companion object {
        @DataProvider(name = "validIds")
        @JvmStatic
        fun validIds(): Array<String?> {
            return arrayOf(
                RandomData.getString(),
                RandomData.getString(MAX_ID_LENGTH),
                RandomData.getNumeric(),
            )
        }

        @DataProvider(name = "invalidIds")
        @JvmStatic
        fun invalidIds(): Array<Array<String>> {
            return arrayOf(
                arrayOf(
                    "",
                    "Build configuration or template ID must not be empty."
                ),
                arrayOf(
                    "\t${RandomData.getString()}   ${RandomData.getString()}",
                    "is invalid: starts with non-letter character"
                ),
                arrayOf(
                    RandomData.getString(MAX_ID_LENGTH + 1),
                    "is invalid: it is ${MAX_ID_LENGTH + 1} characters long while the maximum length is 225."
                ),
                arrayOf(
                    "${RandomData.getString()} \\ ${RandomData.getString()}",
                    "is invalid: contains unsupported character"
                )
            )
        }
    }
}
