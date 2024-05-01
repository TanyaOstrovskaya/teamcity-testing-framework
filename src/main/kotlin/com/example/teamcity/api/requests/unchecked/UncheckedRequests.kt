package com.example.teamcity.api.requests.unchecked

import io.restassured.specification.RequestSpecification

class UncheckedRequests(val userRequest: UncheckedUser,
                        val projectRequest: UncheckedProject,
                        val buildConfigRequest: UncheckedBuildConfig) {

    constructor(spec: RequestSpecification) : this(
        UncheckedUser(spec),
        UncheckedProject(spec),
        UncheckedBuildConfig(spec)
    )
}