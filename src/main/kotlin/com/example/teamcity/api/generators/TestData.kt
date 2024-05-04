package com.example.teamcity.api.generators

import com.example.teamcity.api.models.BuildType
import com.example.teamcity.api.models.NewProjectDescription
import com.example.teamcity.api.models.User
import com.example.teamcity.api.requests.unchecked.UncheckedProject
import com.example.teamcity.api.requests.unchecked.UncheckedUser
import com.example.teamcity.api.spec.Specifications

data class TestData(val user: User,
                    val project: NewProjectDescription,
                    val buildType: BuildType
) {
    fun delete() {
        val spec = Specifications.authSpec(user)
        UncheckedProject(spec).delete(project.id!!)
        UncheckedUser(spec).delete(user.username)
    }
}