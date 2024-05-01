package com.example.teamcity.api.generators

import com.example.teamcity.api.enums.UserRole
import com.example.teamcity.api.models.*

object TestDataGenerator {

    fun generate(): TestData {
        val user = User(
            username = RandomData.getString(),
            password = RandomData.getString(),
            email = "${RandomData.getString()}@gmail.com",
            roles = Roles(
                role = listOf(
                    Role(
                        roleId = "SYSTEM_ADMIN",
                        scope = "g"
                    )
                )
            )
        )

        val projectDescription = NewProjectDescription(
            parentProject = Project(
                locator = "_Root"
            ),
            name = RandomData.getString(),
            id = RandomData.getString(),
            copyAllAssociatedSettings = true
        )

        val buildType = BuildType(
            id = RandomData.getString(),
            name = RandomData.getString(),
            project = projectDescription
        )

        return TestData(
            user = user,
            project = projectDescription,
            buildType = buildType
        )
    }

    fun generateRoles(userRole: UserRole, scope: String): Roles {
        return Roles(
            role = listOf(
                Role(
                    roleId = userRole.text,
                    scope = scope
                )
            )
        )
    }
}