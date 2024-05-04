package com.example.teamcity.api.models

data class NewProjectDescription(val parentProject: Project?,
                                 val name: String?,
                                 val id: String?,
                                 val copyAllAssociatedSettings: Boolean){
}