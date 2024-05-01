package com.example.teamcity.api.models

data class Project(val locator: String,
                   val id: String? = null,
                   val name: String? = null,
                   val parentProjectId: String? = null)