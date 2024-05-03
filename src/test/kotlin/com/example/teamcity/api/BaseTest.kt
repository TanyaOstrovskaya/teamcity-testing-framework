package com.example.teamcity.api

import org.assertj.core.api.SoftAssertions
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod

open class BaseTest {
    protected lateinit var softy: SoftAssertions

    @BeforeMethod
    fun beforeBaseTest() {
        softy = SoftAssertions()
    }

    @AfterMethod
    fun afterBaseTest() {
        softy.assertAll()
    }
}