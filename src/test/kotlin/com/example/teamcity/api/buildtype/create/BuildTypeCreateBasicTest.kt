package com.example.teamcity.api.buildtype.create

import com.example.teamcity.api.BaseApiTest
import com.example.teamcity.api.generators.TestDataStorage
import com.example.teamcity.api.models.Project
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class BuildTypeCreateBasicTest: BaseApiTest() {

    private lateinit var project: Project

    @BeforeMethod
    fun beforeTest() {
        project = checkedWithSuperUser.projectRequest.create(testData.project)
    }

    @Test
    fun buildTypeCanBeCreated() {
        val buildConfig = checkedWithSuperUser.buildConfigRequest.create(testData.buildType)

        softy.assertThat(buildConfig.id).isEqualTo(testData.buildType.id)
    }

    @Test
    fun multipleBuildTypesCanBeCreatedInOneProject() {
        val secondTestData = TestDataStorage.addTestData()
        val firstBuildType = checkedWithSuperUser.buildConfigRequest.create(testData.buildType)
        val secondBuildType = checkedWithSuperUser.buildConfigRequest.create(secondTestData.buildType.copy(project = project))

        softy.assertThat(firstBuildType.id).isEqualTo(testData.buildType.id)
        softy.assertThat(secondBuildType.id).isEqualTo(secondTestData.buildType.id)
        softy.assertThat(firstBuildType.project!!.id).isEqualTo(testData.project.id)
        softy.assertThat(secondBuildType.project!!.id).isEqualTo(testData.project.id)
    }
}