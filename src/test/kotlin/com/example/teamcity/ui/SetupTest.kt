package com.example.teamcity.ui

import com.example.teamcity.ui.pages.StartUpPage
import org.testng.annotations.Test

class SetupTest: BaseUiTest(serverStarted = false) {

    @Test
    fun startUpTest() {
        StartUpPage()
            .open()
            .setUpTeamcityServer()
            .checkHeader("Create Administrator Account")
    }
}