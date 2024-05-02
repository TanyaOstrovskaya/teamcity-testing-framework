package com.example.teamcity.api.requests.checked

import com.example.teamcity.api.requests.checked.admin.CheckedServerAuthSettings
import io.restassured.specification.RequestSpecification

class CheckedRequests(val userRequest: CheckedUser,
                      val projectRequest: CheckedProject,
                      val buildConfigRequest: CheckedBuildConfig,
                      val checkedServerAuthSettings: CheckedServerAuthSettings,
) {

    constructor(spec: RequestSpecification) : this(
        CheckedUser(spec),
        CheckedProject(spec),
        CheckedBuildConfig(spec),
        CheckedServerAuthSettings(spec)
    )
}