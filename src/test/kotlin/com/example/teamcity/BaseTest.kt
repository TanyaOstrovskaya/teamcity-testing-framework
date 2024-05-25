package com.example.teamcity

import com.example.teamcity.api.generators.TestData
import com.example.teamcity.api.generators.TestDataStorage
import com.example.teamcity.api.requests.checked.CheckedRequests
import com.example.teamcity.api.requests.unchecked.UncheckedRequests
import com.example.teamcity.api.spec.Specifications
import org.assertj.core.api.SoftAssertions
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod

open class BaseTest(private val serverStarted: Boolean = true) {
    protected lateinit var softy: SoftAssertions
    protected lateinit var testData: TestData

    val checkedWithSuperUser: CheckedRequests = CheckedRequests(Specifications.superUserSpec())

    val uncheckedWithSuperUser: UncheckedRequests = UncheckedRequests(Specifications.superUserSpec())

    @BeforeMethod
    fun beforeBaseTest() {
        softy = SoftAssertions()
        testData = TestDataStorage.addTestData()
        if (serverStarted) {
            with(checkedWithSuperUser.checkedServerAuthSettings) {
                update(
                    get().copy(perProjectPermissions = "true")
                )
            }
        }
    }

    @AfterMethod
    fun afterBaseTest() {
        TestDataStorage.delete()
        softy.assertAll()
    }
}