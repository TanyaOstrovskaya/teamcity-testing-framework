package com.example.teamcity.ui

import com.codeborne.selenide.Configuration
import com.example.teamcity.BaseTest
import com.example.teamcity.api.config.Config
import com.example.teamcity.api.models.User
import com.example.teamcity.api.requests.checked.CheckedUser
import com.example.teamcity.api.spec.Specifications
import com.example.teamcity.ui.pages.LoginPage
import org.testng.annotations.BeforeSuite


open class BaseUiTest: BaseTest() {

    @BeforeSuite
    fun setupUiTests() {
        Configuration.baseUrl = "http://${Config.getProperty("host")}"
        Configuration.remote = Config.getProperty("remote")

        Configuration.reportsFolder = "target/surefire-reports"
        Configuration.downloadsFolder = "target/downloads"

        BrowserSettings.setup(Config.getProperty("browser"))
    }

    fun loginAsUser(user: User){
        CheckedUser(Specifications.superUserSpec()).create(user)
        LoginPage().open().login(user)
    }
}