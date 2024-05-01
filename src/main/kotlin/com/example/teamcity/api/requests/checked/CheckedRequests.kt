package com.example.teamcity.api.requests.checked

import io.restassured.specification.RequestSpecification

class CheckedRequests(val userRequest: CheckedUser,
                      val projectRequest: CheckedProject,
                      val buildConfigRequest: CheckedBuildConfig
) {

    constructor(spec: RequestSpecification) : this(
        CheckedUser(spec),
        CheckedProject(spec),
        CheckedBuildConfig(spec)
    )
}