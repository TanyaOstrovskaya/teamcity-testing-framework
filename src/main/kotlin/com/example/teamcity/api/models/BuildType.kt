package com.example.teamcity.api.models

data class BuildType(val id: String,
                     val project: NewProjectDescription,
                     val name: String) {
}