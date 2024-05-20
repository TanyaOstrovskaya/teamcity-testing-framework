package com.example.teamcity.ui

import com.codeborne.selenide.Configuration
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxOptions

object BrowserSettings {

    fun setup(browser: String) {
        Configuration.browser = browser

        when (browser) {
            "firefox" -> setFirefoxOptions()
            "chrome" -> setChromeOptions()
        }
    }

    fun setFirefoxOptions() {
        Configuration.browserCapabilities = FirefoxOptions()
        Configuration.browserCapabilities.setCapability("selenoid:options", getSelenoidOptions())
    }

    fun setChromeOptions() {
        Configuration.browserCapabilities = ChromeOptions()
        Configuration.browserCapabilities.setCapability("selenoid:options", getSelenoidOptions())
    }

    fun getSelenoidOptions(): Map<String, Boolean> {
        return mutableMapOf(
            "enableVNC" to true,
            "enableLog" to true
        )
    }
}