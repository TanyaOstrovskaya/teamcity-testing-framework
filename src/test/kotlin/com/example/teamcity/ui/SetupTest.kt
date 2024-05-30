package com.example.teamcity.ui

import com.example.teamcity.api.config.Config
import com.example.teamcity.ui.pages.startup.AgentAuthorizationPage
import com.example.teamcity.ui.pages.startup.AgentsPage
import com.example.teamcity.ui.pages.startup.SuperuserLoginPage
import com.example.teamcity.ui.pages.startup.StartUpPage
import org.testng.annotations.Test

class SetupTest: BaseUiTest(serverStarted = false) {

    @Test
    fun startUpTest() {
        StartUpPage()
            .open()
            .setUpTeamcityServer()
            .checkHeader("Create Administrator Account")
    }

    @Test
    fun startUpTeamcityAgentTest() {
        SuperuserLoginPage()
            .open()
            .loginAsSuperuser(Config.getProperty("superUserToken"))
        AgentAuthorizationPage()
            .open()
            .authorizeNewAgent()
        AgentsPage()
            .checkAgentIsShown()
    }
}