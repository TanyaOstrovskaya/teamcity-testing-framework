package com.example.teamcity.api

import com.example.teamcity.api.generators.TestDataStorage
import com.example.teamcity.api.requests.checked.CheckedProject
import com.example.teamcity.api.requests.checked.CheckedUser
import com.example.teamcity.api.spec.Specifications
import org.testng.annotations.Test

class BuildConfigurationTest: BaseApiTest() {

    @Test
    fun buildConfigurationTest() {
        val testData = TestDataStorage.addTestData()

        CheckedUser(Specifications.superUserSpec())
            .create(testData.user)
        val project = CheckedProject(Specifications.authSpec(testData.user))
            .create(testData.project)
        softy.assertThat(project.id).isEqualTo(testData.project.id)
    }
}