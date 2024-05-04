package com.example.teamcity.api.project.create

import com.example.teamcity.api.BaseApiTest
import org.testng.annotations.Test

class ProjectCreateBasicTest: BaseApiTest() {

    @Test
    fun projectCanBeCreatedBySuperuser() {
        checkedWithSuperUser.userRequest.create(testData.user)
        val project = checkedWithSuperUser.projectRequest.create(testData.project)

        softy.assertThat(project.id).isEqualTo(testData.project.id)
        softy.assertThat(project.name).isEqualTo(testData.project.name)
    }
}