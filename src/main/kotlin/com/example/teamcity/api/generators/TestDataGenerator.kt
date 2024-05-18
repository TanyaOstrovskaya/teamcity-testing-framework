package com.example.teamcity.api.generators

import com.example.teamcity.api.enums.UserRole
import com.example.teamcity.api.models.*

object TestDataGenerator {

    fun generate(): TestData {
        val user = User(
            username = RandomData.getStringLowercase(),
            password = RandomData.getStringLowercase(),
            email = "${RandomData.getStringLowercase()}@gmail.com",
        )

        val projectDescription = NewProjectDescription(
            name = RandomData.getStringLowercase(),
            id = RandomData.getStringLowercase(),
            copyAllAssociatedSettings = true,
            parentProject = null,
        )

        val buildType = BuildType(
            id = RandomData.getStringLowercase(),
            name = RandomData.getStringLowercase(),
            project = Project(
                locator = projectDescription.id!!
            )
        )

        return TestData(
            user = user,
            project = projectDescription,
            buildType = buildType
        )
    }

    fun generateRoles(userRole: UserRole, scope: String) = Roles(
        role = listOf(
            Role(
                roleId = userRole.text,
                scope = scope
            )
        )
    )
}