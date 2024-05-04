package com.example.teamcity.api.project.create.validation

import com.example.teamcity.api.BaseApiTest
import com.example.teamcity.api.enums.UserRole.PROJECT_ADMIN
import com.example.teamcity.api.generators.RandomData
import com.example.teamcity.api.generators.TestDataGenerator
import com.example.teamcity.api.generators.TestDataStorage
import com.example.teamcity.api.requests.checked.CheckedProject
import com.example.teamcity.api.spec.Specifications
import org.apache.http.HttpStatus.SC_BAD_REQUEST
import org.hamcrest.Matchers.containsString
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

private const val MAX_NAME_LENGTH = 1000

class ProjectCreateNameValidationTest: BaseApiTest() {

    @Test(dataProvider = "validNames")
    fun projectWithValidNameCanBeCreated(name: String) {
        val project = checkedWithSuperUser.projectRequest.create(testData.project.copy(name = name))

        softy.assertThat(project.name).isEqualTo(name)
    }

    @Test(dataProvider = "invalidNames")
    fun projectWithInvalidNameCannotBeCreated(name: String?) {
        uncheckedWithSuperUser.projectRequest
            .create(testData.project.copy(name = name))
            .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body(containsString("Project name cannot be empty."))
    }

    @Test
    fun projectWithSameNameCannotBeCreatedForSameUser() {
        checkedWithSuperUser.projectRequest.create(testData.project)
        val secondTestData = TestDataStorage.addTestData()

        uncheckedWithSuperUser.projectRequest
            .create(secondTestData.project.copy(name = testData.project.name))
            .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body(containsString("Project with this name already exists: ${testData.project.name}"));
    }

    @Test
    fun projectWithSameNameCanBeCreatedForAnotherUser() {
        val superUserProject = checkedWithSuperUser.projectRequest.create(testData.project)
        testData.user.roles = TestDataGenerator.generateRoles(PROJECT_ADMIN, "p:${testData.project.id}")
        checkedWithSuperUser.userRequest.create(testData.user)

        val secondTestData = TestDataStorage.addTestData()
        val adminProjectWithSameName = CheckedProject(Specifications.authSpec(testData.user))
            .create(secondTestData.project.copy(
                parentProject = superUserProject,
                name = testData.project.name
            ))
        softy.assertThat(adminProjectWithSameName.name).isEqualTo(testData.project.name)
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
