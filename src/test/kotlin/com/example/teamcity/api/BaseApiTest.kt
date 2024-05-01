package com.example.teamcity.api

import com.example.teamcity.api.generators.TestData
import com.example.teamcity.api.generators.TestDataGenerator
import com.example.teamcity.api.generators.TestDataStorage
import com.example.teamcity.api.requests.checked.CheckedRequests
import com.example.teamcity.api.requests.unchecked.UncheckedRequests
import com.example.teamcity.api.spec.Specifications
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod

open class BaseApiTest: BaseTest() {

    val checkedWithSuperUser: CheckedRequests = CheckedRequests(Specifications.superUserSpec())

    val uncheckedWithSuperUser: UncheckedRequests = UncheckedRequests(Specifications.superUserSpec())


    @BeforeMethod
    fun setupTest() {
//        testDataStorage = TestDataStorage()
    }

    @AfterMethod
    fun cleanTest() {
        TestDataStorage.delete()
    }
}